
provider "google" {
  project = var.gcp_project_id
  region  = var.gcp_region
  # The GitLab CI/CD variable GCP_SA_KEY_JSON will be used for authentication
  credentials = var.gcp_sa_key_json
}

# Look up the project number for IAM bindings
data "google_project" "project" {
  project_id = var.gcp_project_id
}

# The Cloud Run Service Agent needs these permissions to manage resources
resource "google_project_iam_binding" "run_admin_binding" {
  project = var.gcp_project_id
  role    = "roles/run.admin"
  members = [
    "serviceAccount:service-${data.google_project.project.number}@gcp-sa-run.iam.gserviceaccount.com"
  ]
}

resource "google_project_iam_binding" "service_account_user_binding" {
  project = var.gcp_project_id
  role    = "roles/iam.serviceAccountUser"
  members = [
    "serviceAccount:service-${data.google_project.project.number}@gcp-sa-run.iam.gserviceaccount.com"
  ]
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