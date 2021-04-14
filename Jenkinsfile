pipeline {
    agent any
    stages {
        stage('Build The Image') {
            steps {
                sh 'mvn install -Ddocker'
            }
        }

        stage('Push The Image') {
            steps {
                sh 'docker tag teamteach/gateway:latest 333490196116.dkr.ecr.ap-south-1.amazonaws.com/teamteach-gateway'
                sh '$(aws ecr get-login --no-include-email --region ap-south-1)'
                sh 'docker push 333490196116.dkr.ecr.ap-south-1.amazonaws.com/teamteach-gateway'
            }
        }

        stage('Pull and Run (ssh to ec2)') {
            steps {
                sh 'ssh ec2-user@ms.digisherpa.ai \'$(aws ecr get-login --no-include-email --region ap-south-1) ; docker pull 333490196116.dkr.ecr.ap-south-1.amazonaws.com/teamteach-gateway:latest;docker run --net=host -d --name teamteach-gateway 333490196116.dkr.ecr.ap-south-1.amazonaws.com/teamteach-gateway:latest \''
            }
        }

    }
}
