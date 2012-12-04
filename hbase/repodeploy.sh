
mvn -DaltDeploymentRepository=snapshot-repo::default::file:../releases clean deploy -Dmaven.test.skip=true
