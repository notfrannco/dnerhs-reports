pipeline {
 agent any
 stages {
   stage ("Compilar el proyecto devops") {
	   when {
                branch 'devops'
            }
            steps {
             sh """
		sudo podman login registrynexusup.lab.data.com.py -u admin -p Data213347!
              """

             sh """
                sudo podman login registrynexus.lab.data.com.py -u admin -p Data213347!
               """

              sh """
	       sudo podman build -t registrynexusup.lab.data.com.py/dnerhs-reportes . 
	      """
              sh """
               sudo podman push registrynexusup.lab.data.com.py/dnerhs-reportes
              """
           }
   }
   stage ("Compilar el proyecto develop") {
	   when {
                branch 'develop'
            }
            steps {
             sh """
		sudo podman login registrynexusup.lab.data.com.py -u admin -p Data213347!
              """

             sh """
                sudo podman login registrynexus.lab.data.com.py -u admin -p Data213347!
               """

              sh """
	       sudo podman build -t registrynexusup.lab.data.com.py/dnerhs-reportes:dev . 
	      """
              sh """
               sudo podman push registrynexusup.lab.data.com.py/dnerhs-reportes:dev
              """
           }
   }
   stage ("Compilar el proyecto master") {
	   when {
                branch 'master'
            }
            steps {
             sh """
		sudo podman login registrynexusup.lab.data.com.py -u admin -p Data213347!
              """

             sh """
                sudo podman login registrynexus.lab.data.com.py -u admin -p Data213347!
               """

              sh """
	       sudo podman build -t registrynexusup.lab.data.com.py/dnerhs-reportes:prd . 
	      """
              sh """
               sudo podman push registrynexusup.lab.data.com.py/dnerhs-reportes:prd
              """
           }
   }
}
}
