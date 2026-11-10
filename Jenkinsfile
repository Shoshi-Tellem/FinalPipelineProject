pipeline {
    // Same agent label used in the course examples. Change to "agent any" if your Jenkins has no such label.
    agent { label 'verisoft-2' }

    // ---------- User parameters ----------
    parameters {
        string(name: 'REPO_URL',
               defaultValue: 'https://github.com/YOUR_GITHUB_USER/FinalPipelineProject.git',
               description: 'Git repository URL of the tests project')
        string(name: 'BRANCH_NAME',
               defaultValue: 'main',
               description: 'Branch to checkout and run')
    }

    // ---------- Environment ----------
    environment {
        // The branch configured in the Jenkins UI (Configure -> Pipeline -> SCM -> Branch Specifier)
        MAIN_BRANCH = 'main'
    }

    // ---------- Global options ----------
    options {
        // We checkout the code ourselves (checkout scm OR git), so skip the automatic checkout
        skipDefaultCheckout()
    }

    // ---------- Scheduling (Parameterized Scheduler plugin) ----------
    triggers {
        parameterizedCron('''
TZ=Asia/Jerusalem
# Every Monday at 05:30 -> run the main branch
30 5 * * 1 %BRANCH_NAME=main
# Every day at 14:00 -> run the dev branch
0 14 * * * %BRANCH_NAME=dev
''')
    }

    stages {

        // ----- Checkout: same branch as configured in the Jenkins UI -> checkout scm -----
        stage('Checkout (SCM)') {
            when {
                expression { params.BRANCH_NAME == env.MAIN_BRANCH }
            }
            options {
                timeout(time: 5, unit: 'MINUTES')
            }
            steps {
                echo "Starting checkout stage (checkout scm) - branch '${params.BRANCH_NAME}' equals Jenkins branch '${env.MAIN_BRANCH}'"
                checkout scm
                echo 'Checkout stage (checkout scm) completed successfully'
            }
        }

        // ----- Checkout: a different branch -> manual git command -----
        stage('Checkout (Git)') {
            when {
                expression { params.BRANCH_NAME != env.MAIN_BRANCH }
            }
            options {
                timeout(time: 5, unit: 'MINUTES')
            }
            steps {
                echo "Starting checkout stage (git) - cloning ${params.REPO_URL}, branch '${params.BRANCH_NAME}'"
                git branch: "${params.BRANCH_NAME}",
                    url: "${params.REPO_URL}"
                echo 'Checkout stage (git) completed successfully'
            }
        }

        // ----- Compile -----
        stage('Compile') {
            options {
                timeout(time: 5, unit: 'MINUTES')
            }
            steps {
                echo 'Starting compilation stage'
                script {
                    if (isUnix()) {
                        sh 'mvn -B compile'
                    } else {
                        bat 'mvn -B compile'
                    }
                }
                echo 'Compilation stage completed successfully'
            }
        }

        // ----- Tests -----
        stage('Test') {
            options {
                timeout(time: 5, unit: 'MINUTES')
            }
            steps {
                echo 'Starting test stage'
                script {
                    if (isUnix()) {
                        sh 'mvn -B test'
                    } else {
                        bat 'mvn -B test'
                    }
                }
                echo 'Test stage completed successfully'
            }
            post {
                always {
                    // Publish test results in the Jenkins UI
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }
    }

    // ---------- Final status ----------
    post {
        success {
            echo "SUCCESS: Pipeline finished successfully (branch: ${params.BRANCH_NAME})"
        }
        failure {
            echo "FAILURE: Pipeline failed (branch: ${params.BRANCH_NAME})"
        }
        always {
            echo "Pipeline finished with status: ${currentBuild.currentResult}"
        }
    }
}
