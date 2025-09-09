provider "google" {
  project = var.gcp_project_id
  region  = var.gcp_region
}

# Cloud Run service
resource "google_cloud_run_v2_service" "default" {
  name                = var.service_name
  location            = var.gcp_region
  deletion_protection = false

  template {
    containers {
      image = var.gitlab_image_name
      ports {
        container_port = 8080
      }
      resources {
        limits = {
          cpu    = "1"
          memory = "2Gi"
        }
      }
      env {
        name  = "rapidapi.secret"
        value = var.rapidapi_secret
      }
    }
    scaling {
      min_instance_count = 1
      max_instance_count = 5
    }
  }
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