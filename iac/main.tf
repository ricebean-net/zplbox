variable "gcp_project_id" {
  type        = string
  description = "The GCP project ID"
}

variable "gcp_region" {
  type        = string
  description = "The GCP region to deploy to"
  default     = "europe-west1"
}

variable "service_name" {
  type        = string
  description = "The name of the Cloud Run service"
}

variable "gitlab_image_name" {
  type        = string
  description = "The full name of the image in the GitLab Container Registry"
}

variable "gcp_sa_key_json" {
  type        = string
  description = "The JSON content of the GCP service account key"
  sensitive   = true
}

provider "google" {
  project = var.gcp_project_id
  region  = var.gcp_region
  # The GitLab CI/CD variable GCP_SA_KEY_JSON will be used for authentication
  credentials = var.gcp_sa_key_json
}

# Grant Cloud Run the necessary permissions to pull images from the GitLab Container Registry
# This is a critical step for private registries
resource "google_project_iam_binding" "cloud_run_service_account_iam" {
  project = var.gcp_project_id
  role    = "roles/iam.serviceAccountUser"
  members = [
    "serviceAccount:${google_project_service_identity.cloud_run_service_identity.service_account_id}"
  ]
}

resource "google_project_iam_binding" "run_admin_iam" {
  project = var.gcp_project_id
  role    = "roles/run.admin"
  members = [
    "serviceAccount:${google_project_service_identity.cloud_run_service_identity.service_account_id}"
  ]
}

resource "google_project_service_identity" "cloud_run_service_identity" {
  project = var.gcp_project_id
  provider = google
}


# The Cloud Run service
resource "google_cloud_run_v2_service" "default" {
  name     = var.service_name
  location = var.gcp_region

  template {
    containers {
      image = var.gitlab_image_name
    }
  }

  autogenerate_revision_name = true
}

# Allow public (unauthenticated) access to the Cloud Run service
resource "google_cloud_run_v2_service_iam_binding" "default" {
  name     = google_cloud_run_v2_service.default.name
  location = google_cloud_run_v2_service.default.location
  role     = "roles/run.invoker"
  members  = [
    "allUsers"
  ]
}

# The URL of the deployed Cloud Run service
output "cloud_run_url" {
  value       = google_cloud_run_v2_service.default.uri
  description = "The URL of the deployed Cloud Run service"
}