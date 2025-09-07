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

variable "rapidapi_secret" {
  description = "The value for the rapidapi secret environment variable."
  type        = string
  sensitive   = true
}