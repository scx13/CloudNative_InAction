- The Docker Engine has a client/server architecture
- Container images are the product of executing an ordered sequence of instructions, each resulting in a layer. Each image is made up of several layers. Each layer represents a modification produced by the corresponding instruction. The final artifact, an image, can be run as a container.

## Creating images with Dockerfiles
- docker login ghcr.io -> auth to a container registry in this case github
- docker tag my-java-image:1.0.0 ghcr.io/<your_github_username>/my-java-image:1.0.0
- docker push ghcr.io/<your_github_username>/my-java-image:1.0.0

## Packaging Spring Boot applications as container images
- Packaging a Spring Boot application as a container image means that the application will run in an isolated context, including computational resources and network
- By default, containers join an isolated network inside the Docker host. If you want to access any container from your local network, you must explicitly configure the port mapping
- 