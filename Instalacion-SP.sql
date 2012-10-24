SELECT sqlj.remove_jar('pruebas',true);
SELECT sqlj.install_jar('file:///home/damian/repositorioGit/prototipo/build/classes/storedProcedures.jar','SP',true);

SELECT sqlj.set_classpath('public','SP');

SELECT sqlj.get_classpath('public');