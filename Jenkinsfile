pipeline {
    agent any
    environment {
        PROJECT = "teamteach-gateway"
        USER = "ec2-user"
        REGION = "$REGION"
        ECR_LOGIN = "aws ecr get-login --no-include-email --region $REGION"
    }
    stages{
        stage('Build') {
            when { anyOf {
                expression { env.GIT_BRANCH == env.BRANCH_ONE }
                expression { env.GIT_BRANCH == env.BRANCH_TWO }
            } }
            steps {
                sh 'echo $GIT_BRANCH'
                sh "mvn install -Ddocker -Dbranch=${GIT_BRANCH}"
            }
        }
        stage('Push to ECR') {
            when { anyOf {
                expression { env.GIT_BRANCH == env.BRANCH_ONE }
                expression { env.GIT_BRANCH == env.BRANCH_TWO }
            } }
            steps {
                sh 'docker images'
                sh 'docker tag ${PROJECT}:${GIT_BRANCH} ${AWS_REPO}/${PROJECT}:${GIT_BRANCH}'
                sh '$($ECR_LOGIN)'
                sh "docker push ${AWS_REPO}/${PROJECT}:${GIT_BRANCH}"
            }
        }
        stage('Pull & Run') {
            when { anyOf {
                expression { env.GIT_BRANCH == env.BRANCH_ONE }
                expression { env.GIT_BRANCH == env.BRANCH_TWO }
            } }
            steps {
                sh 'echo \$(${ECR_LOGIN}) > ${GIT_BRANCH}.sh'
                sh 'echo docker pull $AWS_REPO/$PROJECT:$GIT_BRANCH >> ${GIT_BRANCH}.sh'
                sh 'echo docker rm -f $PROJECT >> ${GIT_BRANCH}.sh'
                sh 'echo docker run -p 8080:8080 -d --name $PROJECT $AWS_REPO/$PROJECT:$GIT_BRANCH >> ${GIT_BRANCH}.sh'
                sh 'cat ${GIT_BRANCH}.sh'
                sh 'cat ${GIT_BRANCH}.sh | ssh ${USER}@$GIT_BRANCH.$DOMAIN' 
            }
        }
    }
}
