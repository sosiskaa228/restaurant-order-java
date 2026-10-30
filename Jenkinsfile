pipeline {
    agent any

    parameters {
        choice(
            name: 'DELIVERY_MODE',
            choices: ['day', 'night'],
            description: 'Режим доставки. Выберите значение перед запуском Pipeline.'
        )
    }

    stages {
        stage('Build') {
            steps {
                withEnv(["PATH+MAVEN=D:\\Programs\\Maven\\apache-maven-3.9.16\\bin"]) {
                    bat 'mvn compile'
                }
            }
        }

        stage('Test') {
            steps {
                withEnv(["PATH+MAVEN=D:\\Programs\\Maven\\apache-maven-3.9.16\\bin"]) {
                    bat 'mvn test'
                }
            }
        }

        stage('Package') {
            steps {
                withEnv(["PATH+MAVEN=D:\\Programs\\Maven\\apache-maven-3.9.16\\bin"]) {
                    script {
                        if (params.DELIVERY_MODE == 'night') {
                            bat 'mvn package -DskipTests -Pnight'
                        } else {
                            bat 'mvn package -DskipTests'
                        }
                    }
                }
                echo "Параметр DELIVERY_MODE = ${params.DELIVERY_MODE}"
            }
        }
    }
}