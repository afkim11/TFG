**Nota importante:** La versión Java requerida es mínimo 1.7.

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
  Actualmente, tenemos un unico archivo de configuracion(lo que hay que poner en _**Program Arguments**_):
  	- "descripcion_4Robots_6VictimasIP_001_Frecuencia10seg_Jerarquico
  - Por último, hay que aumentar el tamaño del stack(la pila de llamadas) porque se agota con el tamaño por defecto. Para aumentar el tamaño de la pila
  hay que añadir en _**VM Arguments**_ lo siguiente: _-Xss32m_.

**Últimos pasos:**
	El último paso es cambiar la codificación y la versión de Java del proyecto, para ello clickamos con el botón derecho en el proyecto en eclipse(o cualquier IDE usado) y seleccionamos propiedades; una vez ahí, vamos a **Resource** y en el apartado _Text file encoding_, seleccionamos _others_ y elegimos **Cp1252**. Para cambiar la versión, en el mismo menú seleccionamos el apartado _Java compiler_ y ahí activamos _Enable project specific settings_ y seleccionamos en _Compiler compliance level_ el **1.8**.
  Si todo ha ido bien las configuraciones y el proyecto os debe quedar como en las fotos de a continuacion:

  Proyecto en eclipse --> https://pixabay.com/photo-1089640/
  
  Configuracion(Main) --> https://pixabay.com/photo-1089641/
  
  Configuracion(Arguments) --> https://pixabay.com/photo-1089642/
