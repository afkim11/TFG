Instrucciones ejecución del proyecto en Eclipse:
	Este proyecto incorpora los archivos que eclipse genera cuando creas un proyecto(classpath y .project), por lo que no es necesario crear un proyecto para incorporar este proyecto.
	Lo que hay que hacer es lo siguiente:
		-File -> Import...
		-Seleccionamos la import source "Existing Projects into Workspace" y damos a Next
		-Ahora, en la ventana Import Projects, seleccionamos la pestaña Select root directory, y damos a "browse".
		-Aquí tenemos que seleccionar la carpeta que contiene el proyecto(la carpeta que contiene las demás carpetas src,config,lib,etc...)
		-Finalmente damos a finalizar. 
		Con esto ya tendriamos importado el proyecto correctamente al eclipse, pero aun faltaría algo más: las librerias. 
		Github no sincroniza al repositorio los archivos de librerias, por lo que los tienes que añadir tu a la carpeta lib manualmente. 
		Se facilitan las librerias que se usan en el siguiente enlace: 
			https://drive.google.com/file/d/0B1svaDEcqxkaU00wSzJGSHAzX0E/view?usp=sharing
		
		Es un zip, descomprimimos y contiene una carpeta lib que son todas las librerias, y las copiamos al directorio lib del proyecto.
		
		
		Si se han seguido correctamente estos pasos tendréis el proyecto instalado correctamente en eclipse, y ya solo quedaria la ejecución.
		
		