**Nota importante:** La versión Java requerida es 1.7. Con Java 1.8 es posible que ocasione algunos errores en tiempo de ejecución. 

**Instrucciones para ejecutar el proyecto.** 

Este proyecto incorpora los archivos que eclipse genera cuando creas un proyecto(classpath y .project), por lo que no es necesario crear un proyecto para incorporarlo. 
Lo que hay que hacer es lo siguiente: 

	1. File -> Import...
	2. Seleccionamos la import source "Existing Projects into Workspace" y damos a Next
	3. Ahora, en la ventana Import Projects, seleccionamos la pestaña Select root directory, y damos a "browse"
	4. Aquí tenemos que seleccionar la carpeta que contiene el proyecto(la carpeta que contiene las demás carpetas src,config,lib,etc...) 

 Si se han seguido correctamente estos pasos tendréis el proyecto instalado correctamente en eclipse, y ya solo quedaria la ejecución.

**Ejecución**: Para realizar la ejecución correctamente en eclipse hay que crear un perfil de ejecución del proyecto
  lanzando el main de la clase _"ArranqueSistemaConCrtlGestorO"_ localizada en _"icaro.infraestructura.clasesGeneradorasOrganizacion."_ y 
  pasándole como argumentos el nombre del xml de la organizacion a ejecutar.

  **Paso a paso:**
  - Una vez instalado el proyecto correctamente en eclipse,clicamos en la flecha del boton de _"run"_ verde que hay en la parte de arriba y seleccionamos _**Run configurations...**_.
  - Nos saldrá una ventana de configuraciones, y en la parte izquierda nos aparece una serie de tipos de ejecución; ahí tenemos que clicar derecho en _**java application**_ y darle a _**new**_.
  - Ahora, tenemos que configurar la configuración:
  - En la pestaña Main tenemos que seleccionar en _**Project**_ nuestro proyecto y despues en _**Main Class**_ buscamos la clase mencionada anteriormente
   o directamente ponemos _"icaro.infraestructura.clasesGeneradorasOrganizacion.ArranqueSistemaConCrtlGestorO"_.
  - En la pestaña Arguments tenemos que ir al cuadro de texto _**Program Arguments**_ y escribir ahí el nombre del xml de configuración que queramos ejecutar.
  Hay varias descripciones de organización, pero para realizar pruebas recomendamos la siguiente(lo que hay que poner en _**Program Arguments**_):
  	- descripcion_Pruebas

**Últimos pasos:**
	En el caso de que haya problemas en la compilación por problemas de codificación de carácteres, habría que cambiar la codificación, para ello clicamos con el botón derecho en el proyecto en eclipse(o cualquier IDE usado) y seleccionamos propiedades; una vez ahí, vamos a **Resource** y en el apartado _Text file encoding_, seleccionamos _others_ y elegimos **Cp1252**. 
  Si todo ha ido bien las configuraciones y el proyecto os debe quedar como en las fotos de a continuacion:

  Proyecto en eclipse -->
  
  <a href="http://es.tinypic.com?ref=2vwfxmw" target="_blank"><img src="http://i63.tinypic.com/2vwfxmw.jpg" border="0" alt="Image and video hosting by TinyPic"></a>
  
  Configuracion(Main) -->
  
  <a href="http://es.tinypic.com?ref=bg1qc8" target="_blank"><img src="http://i68.tinypic.com/bg1qc8.jpg" border="0" alt="Image and video hosting by TinyPic"></a>
  
  Configuracion(Arguments) --> 

<a href="http://es.tinypic.com?ref=k55rus" target="_blank"><img src="http://i67.tinypic.com/k55rus.jpg" border="0" alt="Image and video hosting by TinyPic"></a>
