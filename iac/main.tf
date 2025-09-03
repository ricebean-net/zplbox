provider "google" {
  project = var.gcp_project_id
  region  = var.gcp_region
}

# Look up the project number for IAM bindings
data "google_project" "project" {
  project_id = var.gcp_project_id
}

# Enable required APIs
resource "google_project_service" "cloud_run_api" {
  project = var.gcp_project_id
  service = "run.googleapis.com"
}

resource "google_project_service" "artifact_registry_api" {
  project = var.gcp_project_id
  service = "artifactregistry.googleapis.com"
}

# The Cloud Run Service Agent needs these permissions to manage resources
resource "google_project_iam_binding" "run_admin_binding" {
  project = var.gcp_project_id
  role    = "roles/run.admin"
  members = [
    "serviceAccount:service-${data.google_project.project.number}@gcp-sa-run.iam.gserviceaccount.com"
  ]
  depends_on = [google_project_service.cloud_run_api]
}

resource "google_project_iam_member" "service_account_user" {
  project = var.gcp_project_id
  role    = "roles/iam.serviceAccountUser"
  member  = "serviceAccount:service-${data.google_project.project.number}@gcp-sa-run.iam.gserviceaccount.com"

  depends_on = [google_project_service.cloud_run_api]
}

# Grant the Cloud Run Service Agent permission to read from Artifact Registry
resource "google_project_iam_member" "artifact_registry_reader" {
  project = var.gcp_project_id
  role    = "roles/artifactregistry.reader"
  member  = "serviceAccount:service-${data.google_project.project.number}@gcp-sa-run.iam.gserviceaccount.com"

  depends_on = [
    google_project_service.cloud_run_api,
    google_project_service.artifact_registry_api
  ]
}

# The Cloud Run v2 service
resource "google_cloud_run_v2_service" "default" {
  name     = var.service_name
  location = var.gcp_region

  template {
    containers {
      image = var.gitlab_image_name
    }
  }

  depends_on = [
    google_project_service.cloud_run_api,
    google_project_service.artifact_registry_api
  ]
}

# Allow public (unauthenticated) access to the Cloud Run service
resource "google_cloud_run_v2_service_iam_binding" "default" {
  name     = google_cloud_run_v2_service.default.name
  location = google_cloud_run_v2_service.default.location
  role     = "roles/run.invoker"
  members  = ["allUsers"]

  depends_on = [google_cloud_run_v2_service.default]
}

# The URL of the deployed Cloud Run service
output "cloud_run_url" {
  value       = google_cloud_run_v2_service.default.uri
  description = "The URL of the deployed Cloud Run service"
}