pipeline {
    agent any

    environment {
        PROJECT = "teamteach-gateway"
        USER = "ec2-user"
    }
    
    stages{
        stage('Build') {
            steps {
                sh 'echo $GIT_BRANCH'
                sh "mvn install -Ddocker -Dbranch=${GIT_BRANCH}"
            }
        }
        stage('Push to ECR') {
            steps {
                sh 'docker tag ${PROJECT}:${GIT_BRANCH} ${AWS_REPO}/${PROJECT}:${GIT_BRANCH}'
                sh '$(aws ecr get-login --no-include-email --region ap-south-1)'
                sh "docker push ${REPO}/${PROJECT}:${GIT_BRANCH}"
            }
        }
        stage('Pull & Run') {
            steps {
                sh 'echo \'$(aws ecr get-login --no-include-email --region ap-south-1)\' > ${GIT_BRANCH}.sh'
                sh 'echo docker pull $AWS_REPO/$PROJECT:$GIT_BRANCH >> ${GIT_BRANCH}.sh'
                sh 'echo docker rm -f $PROJECT >> ${GIT_BRANCH}.sh'
                sh 'echo docker run -e TZ=$TIMEZONE --net=host -p 8080:8080 -d --name $PROJECT $AWS_REPO/$PROJECT:$GIT_BRANCH >> ${GIT_BRANCH}.sh'
                sh 'cat ${GIT_BRANCH}.sh | ssh ${USER}@${GIT_BRANCH}.$MS_DOMAIN' 
            }
        }
        stage('Cleanup') {
            steps {
                sh 'ssh ${USER}@${GIT_BRANCH}.$MS_DOMAIN \'$(aws ecr get-login --no-include-email --region ap-south-1) ; docker image prune -a \''
            }
        }
    }
}
