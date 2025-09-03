terraform {
  backend "http" {
    # No configuration is needed here for the GitLab CI/CD environment.
    # The address and credentials will be set automatically by the pipeline.
  }
}