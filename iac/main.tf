provider "google" {
  project = var.gcp_project_id
  region  = var.gcp_region
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
  depends_on = [google_cloud_run_v2_service.default]
}

resource "google_project_iam_binding" "service_account_user_binding" {
  project = var.gcp_project_id
  role    = "roles/iam.serviceAccountUser"
  members = [
    "serviceAccount:service-${data.google_project.project.number}@gcp-sa-run.iam.gserviceaccount.com"
  ]
  depends_on = [google_cloud_run_v2_service.default]
}

# This grants the Cloud Run Service Agent permission to read from Artifact Registry
# resource "google_project_iam_binding" "artifact_registry_reader_binding" {
#   project = var.gcp_project_id
#   role    = "roles/artifactregistry.reader"
#   members = [
#     "serviceAccount:service-${data.google_project.project.number}@gcp-sa-run.iam.gserviceaccount.com"
#   ]
#   depends_on = [google_cloud_run_v2_service.default]
# }

# This creates a secret with your GitLab credentials for Cloud Run to use
resource "google_cloud_run_v2_service_template_secret" "gitlab_auth" {
  location = var.gcp_region
  service  = google_cloud_run_v2_service.default.name
  name     = "gitlab-auth"

  secret {
    secret_name = "gitlab-auth"
    username    = "gitlab-ci-token" # Or your deploy token's username
    password    = "$CI_JOB_TOKEN"   # Or your deploy token's value
  }
}

# The Cloud Run v2 service
resource "google_cloud_run_v2_service" "default" {
  name     = var.service_name
  location = var.gcp_region

  template {
    containers {
      image = "registry.gitlab.com/ricebean.net/zplbox/zplbox:1.0.4" # The original GitLab image path
    }
    # This block tells Cloud Run where to find the auth secret
    image_pull_secrets {
      secret_name = google_cloud_run_v2_service_template_secret.gitlab_auth.name
    }
  }
}

# Allow public (unauthenticated) access to the Cloud Run service
resource "google_cloud_run_v2_service_iam_binding" "default" {
  name     = google_cloud_run_v2_service.default.name
  location = google_cloud_run_v2_service.default.location
  role     = "roles/run.invoker"
  members = [
    "allUsers"
  ]
}

# The URL of the deployed Cloud Run service
output "cloud_run_url" {
  value       = google_cloud_run_v2_service.default.uri
  description = "The URL of the deployed Cloud Run service"
}