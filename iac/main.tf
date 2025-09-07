provider "google" {
  project = var.gcp_project_id
  region  = var.gcp_region
}

# Cloud Run service
resource "google_cloud_run_v2_service" "default" {
  name     = var.service_name
  location = var.gcp_region

  template {
    containers {
      image = var.gitlab_image_name
      env {
        name  = "rapidapi.semax_instance_countcret"
        value = var.rapidapi_secret
      }
    }
    scaling {
      min_instance_count = 1
      max_instance_count = 5
    }
  }

  deletion_protection = false
}

# Allow public access
resource "google_cloud_run_v2_service_iam_member" "public_invoker" {
  name     = google_cloud_run_v2_service.default.name
  location = google_cloud_run_v2_service.default.location
  role     = "roles/run.invoker"
  member   = "allUsers"
}

# Output the Cloud Run URL
output "cloud_run_url" {
  value       = google_cloud_run_v2_service.default.uri
  description = "The URL of the deployed Cloud Run service"
}