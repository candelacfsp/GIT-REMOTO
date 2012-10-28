--SELECT sqlj.remove_jar('SP',true);
SELECT sqlj.install_jar('file:///home/rodrigo/repositorioGit/prototipo/build/classes/storedProcedures.jar','SP',true);

SELECT sqlj.set_classpath('public','SP');

SELECT sqlj.get_classpath('public');