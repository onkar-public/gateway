pipeline {
    agent { label 'test-automation' }
    stages {
        stage('Build The Image') {
            steps {
                sh '/opt/maven/bin/mvn --version'
                sh '/opt/maven/bin/mvn install -Ddocker'
            }
        }

        stage('Push The Image') {
            steps {
                sh '/usr/bin/docker tag digishaala/gateway:latest digishaala/gateway:latest'
                sh '/usr/bin/docker push digishaala/gateway:latest'
            }
        }

        stage('Pull and Run (ssh to ec2)') {
            steps {
                sh '/opt/bin/drm digishaala gateway'
                sh '/usr/bin/docker-compose -f docker-compose.yml up -d'
            }
        }

    }
    environment {
        JAVA_HOME="/usr/lib/jvm/java-14-openjdk-amd64"
    }
}
