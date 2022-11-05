- Cluster a set of nodes running containerized applications
  - It host the Control Plane and comprises one or more worker nodes
- Control Plane : a cluster component exposing API and interfaces to define,deploy,manage lifecycle of pods
- Worker nodes : physical or virtual machines
- Pod : smallest deployable unit

# Minikube
- minikube start --cpus 2 --memory 4g --driver docker --profile polar
  - start a cluster on top of docker

- kubectl get nodes
  - list all nodes

- kubectl config get-contexts
  - list all available contexts

- kubectl config current-context
  - verify current context

- kubectl config use-context polar
  - change current context to polar

- minikube stop --profile polar
  - stop cluster polar

- minikube delete --profile polar
  - delete cluster polar

- minikube start --profile polar
  - start cluster polar

- kubectl apply -f services
  - deploy services configures in the yml to the current context

- kubectl logs deployment/polar-postgres
  - checkout logs

- kubectl delete -f services
  - delete services

# Pod vs container
- a deployment is an object that manages the lifecycle of a stateless replicated application
  - each replica is represented by a pod
  - replica is distributed among the nodes of a cluster for better resilience

- you dont manage pods, you let the deployment do that for you

# Creating a deployment for a spring boot app
- use manifest files

- manifest files 4 sections:
  - apiVersion : version of kube API used to create this object
  - kind : what kind of object
  - metadata : id the object
  - spec : the state desired by your object

- minikube image load catalog-service --profile polar
  - load local image into cluster