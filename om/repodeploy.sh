
mvn -DaltDeploymentRepository=snapshot-repo::default::file:../releases clean install deploy -Dmaven.test.skip=true
