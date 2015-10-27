//copia de jefe
package icaro.aplicaciones.agentes.agenteAplicacionrobotMasterIAReactivo.comportamiento;


import icaro.aplicaciones.agentes.agenteAplicacionrobotMasterIAReactivo.comportamiento.*;
import icaro.aplicaciones.informacion.dominioClases.robotMasterIA.EvaluacionAgente;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.HebraMonitorizacion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;


public class AccionesSemanticasAgenteAplicacionrobotMasterIA extends AccionesSemanticasAgenteReactivo {



	private ItfUsoAgenteReactivo agenteAcceso;
        private ArrayList agentesEquipo, respuestasAgentes,confirmacionesAgentes,nuevasEvaluacionesAgentes,empates;//resto de agentes que forman mi equipo
        private HebraMonitorizacion tiempoSinRecibirRespuesta;
        private int mi_eval, mi_eval_nueva;
	private ItfUsoVisualizadorAcceso visualizacion;
        private ComunicacionAgentes comunicacion ;
        private EvaluacionAgente miEvaluacion ;


	public void arranque(){
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Arrancamos el agente" , InfoTraza.NivelTraza.debug));
            try {
                this.informaraMiAutomata("mandaEvaluacionATodos", null);
//                agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
//			(NombresPredefinidos.ITF_USO+this.nombreAgente);
//                agenteAcceso.aceptaEvento(new EventoRecAgte("mandaEvaluacionATodos", this.nombreAgente, this.nombreAgente));
                tiempoSinRecibirRespuesta = new HebraMonitorizacion(5000,this.itfUsoPropiadeEsteAgente, "5segSinRespuesta");
                comunicacion = new ComunicacionAgentes(nombreAgente);
            } catch (Exception ex) {
                Logger.getLogger(AccionesSemanticasAgenteAplicacionrobotMasterIA.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

        public void mandarEvalATodos(){
        try {
            Evaluacion evaluacionAgentes = Evaluacion.dameEvaluacionAgentes();
            //Recuperar la evaluacion del fichero de evaluacion y enviarla a los demas
            Integer k = new Integer(-1);
            if (this.nombreAgente.contains("1")) {
                k = evaluacionAgentes.dameDistanciaAgentes(0);
            } else if (this.nombreAgente.contains("2")) {
                k = evaluacionAgentes.dameDistanciaAgentes(1);
            } else if (this.nombreAgente.contains("3")) {
                k = evaluacionAgentes.dameDistanciaAgentes(2);
            } else {
                k = evaluacionAgentes.dameDistanciaAgentes(3);
            }
            mi_eval = k.intValue();
            mi_eval_nueva = Integer.MAX_VALUE;//como va el que menor rango tiene, lo inicializamos a la peor

            //ademas, inicializamos el numero de agentes que hay en el sistema
  //          ArrayList agentesRegistrados = this.itfUsoRepositorio.nombresInterfacesRegistradas();
            ArrayList agentesRegistrados = this.itfUsoRepositorio.nombresAgentesAplicacionRegistrados();
            String aux;
            agentesEquipo = new ArrayList();
            respuestasAgentes = new ArrayList();
            confirmacionesAgentes = new ArrayList();
            empates = new ArrayList();
            nuevasEvaluacionesAgentes = new ArrayList();
            for (int i = 0; i < agentesRegistrados.size(); i++) {
                aux = (String) agentesRegistrados.get(i);

                //filtramos los Gestores. Mandamos solo a las itf de uso de los agentes, y excluimos el propio agente
          //      if (aux.contains("robotMasterIA") && aux.contains("Itf_Uso") && !aux.contains(this.nombreAgente)) {
                   if (aux.contains("robotMasterIA")  && !aux.contains(this.nombreAgente)) {
    //                agentesEquipo.add(itfUsoRepositorio.obtenerInterfaz(aux).toString());
                    agentesEquipo.add(aux);
                    //del resto lo rellenamos con strings vacios que se modificaran cuando recibamos las respuestas
                    respuestasAgentes.add("");
                    confirmacionesAgentes.add("");
                    nuevasEvaluacionesAgentes.add("");
                }
            }
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Enviamos la evaluacion " + k, InfoTraza.NivelTraza.debug));
//            this.mandaMensajeATodos("respuesta", k);
             miEvaluacion = new EvaluacionAgente(nombreAgente,k);
            comunicacion.informaraGrupoAgentes(new InfoContEvtMsgAgteReactivo("respuesta", miEvaluacion), agentesEquipo);
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Numero de agentes de los que espero respuesta:" + agentesEquipo.size(), InfoTraza.NivelTraza.debug));
            tiempoSinRecibirRespuesta.start();
        } catch (Exception ex) {
            Logger.getLogger(AccionesSemanticasAgenteAplicacionrobotMasterIA.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        public int numRespuestasRecibidas(ArrayList respuestas){
            int respRecibidas = 0;
            for(int i = 0; i< respuestas.size(); i++){
                if(!((String)respuestas.get(i)).equals("")){
                    respRecibidas++;
                }
            }
            return respRecibidas;
        }

        public boolean tengoLaMejorEval(ArrayList respuestas){
            boolean soyElMejor = true;
            for(int i = 0; i< respuestas.size();i++){
                if(mi_eval<Integer.parseInt((String)respuestas.get(i))){
                    soyElMejor = false;
                }
            }
            return soyElMejor;
        }

        //El que tiene mejor evaluacion nueva es el que menor Id tiene
        public boolean tengoLaMejorEvalNueva(ArrayList respuestas){
            boolean soyElMejor = true;
            for(int i = 0; i< respuestas.size();i++){
                String auxiliar = (String)respuestas.get(i);
                if(!auxiliar.equals("")){
                    if(mi_eval_nueva>Integer.parseInt(auxiliar)){
                        soyElMejor = false;
                    }
                }
            }
            return soyElMejor;
        }

        //funcion que sirve para ver con cuantos empata
        public boolean hayEmpate(ArrayList respuestas){
            boolean hayEmpate = false;
            for(int i = 0; i< respuestas.size();i++){
                if(mi_eval == Integer.parseInt((String)respuestas.get(i))){
                    hayEmpate = true;
                    empates.add(new Integer(i));//quiere decir que el agente de agentesequipo(i) esta empatado conmigo
                    //nuevasEvaluacionesAgentes.add("");//Anadimos un hueco donde va a ir la nueva evaluacion del agente
                }
            }
            return hayEmpate;
        }

        //evidentemente, este metodo no se invoca cuando el mejor soy yo. Se llama en notificar mejor.
        //devuelve el agente mejor dentro de mi equipo
        public String dameMejor(ArrayList respuestas){
            String mejorAgente = (String)agentesEquipo.get(0);
            int mejor_eval = Integer.parseInt((String)respuestas.get(0));
            int evaluacion_local;

            //empezamos en el uno porque lo hemos inicializado en el cero
            for(int i = 1; i< respuestas.size();i++){
                evaluacion_local = Integer.parseInt((String)respuestas.get(i));
                if(mejor_eval<evaluacion_local){
                    mejorAgente = (String)agentesEquipo.get(i);
                    mejor_eval = evaluacion_local;
                }
            }
            return mejorAgente;
        }

	public void validaNumRespuestas(EvaluacionAgente evaluacionRecibida) {
//            ArrayList p = paquete;
//            String eval = (String)p.get(0);
            Integer eval =  evaluacionRecibida.getEvaluacion();
//            String fuente = (String) p.get(1);
            String fuente = evaluacionRecibida.getIdentAgente();
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Respuesta Recibida. Dice :" + eval+" Emisor: "+fuente, InfoTraza.NivelTraza.debug));
            //actualizar las respuestas
            respuestasAgentes.set(agentesEquipo.indexOf(fuente), eval.toString());//guardamos la respuesta

            tiempoSinRecibirRespuesta.finalizar();
            //si tenemos todas las respuestas, transitar al estado adecuado
            if(numRespuestasRecibidas(respuestasAgentes) == agentesEquipo.size()){
                //si tengo todas las respuestas, evaluamos si soy o no el mejor
                try{
                    if(tengoLaMejorEval(respuestasAgentes)){
                        if(hayEmpate(respuestasAgentes)){
//                            agenteAcceso.aceptaEvento(new EventoRecAgte("yaTengoTodasLasRespuestasHayEmpate", this.nombreAgente, this.nombreAgente));
                              this.informaraMiAutomata("yaTengoTodasLasRespuestasHayEmpate", null);
                            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Tengo todas las respuestas y hay empate", InfoTraza.NivelTraza.debug));
                        }
                        else{
//                            agenteAcceso.aceptaEvento(new EventoRecAgte("yaTengoTodasLasRespuestasYSoyElMejor", this.nombreAgente, this.nombreAgente));
                           this.informaraMiAutomata("yaTengoTodasLasRespuestasYSoyElMejor", null);
                            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Tengo todas las respuestas y soy el mejor", InfoTraza.NivelTraza.debug));
                        }
                    }
                    else{
                            this.informaraMiAutomata("yaTengoTodasLasRespuestasYNoSoyElMejor", null);
                            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Tengo todas las respuestas y no soy el mejor", InfoTraza.NivelTraza.debug));
                    }
                }catch(Exception e){
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Error al validar la respuesta", InfoTraza.NivelTraza.error));
                }
                return;
            }
            //reiniciamos el tiempo sin recibir respuesta
            tiempoSinRecibirRespuesta = new HebraMonitorizacion(5000,this.itfUsoPropiadeEsteAgente, "5segSinRespuesta");
            tiempoSinRecibirRespuesta.start();

        }

        //miramos las respuestas que tenemos y pedimos a los que nos faltan que nos den las respuestas.
        public void peticionRecibirRespuestasRestantes(){
            //mandar un mensaje a los agentes que no nos han enviado la respuesta aun
            //enviamos el mensaje y la info adicional, que es de donde viene
            try {
                for(int i = 0; i< respuestasAgentes.size(); i++){
                    if(((String)respuestasAgentes.get(i)).equals("")){//si aun no tenemos respuesta, queremos que nos vuelva a mandar las cosas
                        String agente = (String) agentesEquipo.get(i);
//                        mandaMensajeAAgenteId("reenviaDatos", nombreAgente, agente);
                        comunicacion.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo("reenviaDatos",nombreAgente), agente);
                    }
                }
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Pedimos que nos reenvien los datos ", InfoTraza.NivelTraza.debug));
            } catch (Exception ex) {
               trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Error al enviar peticionRecibirRespuestasRestantes "+ ex, InfoTraza.NivelTraza.error));
            }
        }

        public void peticionRecibirConfirmacionesRestantes(){
            //mandar un mensaje a los agentes que no nos han enviado la respuesta aun
            //enviamos el mensaje y la info adicional, que es de donde viene
            try {
                for(int i = 0; i< confirmacionesAgentes.size(); i++){
                    if(((String)confirmacionesAgentes.get(i)).equals("")){//si aun no tenemos respuesta, queremos que nos vuelva a mandar las cosas
                        String agente = (String) agentesEquipo.get(i);
//                        mandaMensajeAAgenteId("reenviarConfirmacion", nombreAgente, agente);
                        comunicacion.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo("reenviarConfirmacion",nombreAgente), agente);
                    }
                }
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Pedimos que nos reenvien la confirmacion ", InfoTraza.NivelTraza.debug));
            } catch (Exception ex) {
               trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Error al enviar peticionRecibirConfirmacionessRestantes "+ ex, InfoTraza.NivelTraza.error));
            }
        }

        public void peticionRecibirEvaluacionesRestantes(){
            //mandar un mensaje a los agentes que no nos han enviado la nueva evaluacion aun
            //enviamos el mensaje y la info adicional, que es de donde viene
            try {
                for(int i = 0; i< empates.size(); i++){
                    if(((String)nuevasEvaluacionesAgentes.get(i)).equals("")){//si aun no tenemos respuesta, queremos que nos vuelva a mandar las cosas
                        String agente = (String) agentesEquipo.get((Integer)empates.get(i));
//                        mandaMensajeAAgenteId("reenviaNuevaEvaluacion", nombreAgente, agente);
                        comunicacion.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo("reenviaNuevaEvaluacion",nombreAgente), agente);
                    }
                }
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Pedimos que nos reenvien las evaluaciones", InfoTraza.NivelTraza.debug));
            } catch (Exception ex) {
               trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Error al enviar peticionRecibirConfirmacionessRestantes "+ ex, InfoTraza.NivelTraza.error));
            }
        }

        public void accionNula(){

        }

        public void inicializarTimeOut(){
            if(tiempoSinRecibirRespuesta.isAlive()){
                tiempoSinRecibirRespuesta.finalizar();
            }
            tiempoSinRecibirRespuesta = new HebraMonitorizacion(5000,this.itfUsoPropiadeEsteAgente, "5segSinRespuesta");
            tiempoSinRecibirRespuesta.start();
        }

        public void notificarMejor(){
            String mejor = this.dameMejor(respuestasAgentes);
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, " Notificamos al mejor( "+mejor+" ), que vaya el ", InfoTraza.NivelTraza.debug));
//            this.mandaMensajeAAgenteId("confirmacion", this.nombreAgente, mejor);
            comunicacion.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo("confirmacion",this.nombreAgente), mejor);
        }

        //metodo parecido a validaNumrespuestas. De hecho, es identico pero con confirmaciones
        //Recibimos el agente que nos manda la confirmacion
        public void validaNumConfirmaciones(String fuente){

            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Confirmacion Recibida. Fuente: "+fuente, InfoTraza.NivelTraza.debug));
            //actualizar las respuestas
            confirmacionesAgentes.set(agentesEquipo.indexOf(fuente), "OK");//guardamos las confirmaciones

            if(tiempoSinRecibirRespuesta.isAlive())
                tiempoSinRecibirRespuesta.finalizar();
            //si tenemos todas las respuestas, transitar al estado adecuado
            if(numRespuestasRecibidas(confirmacionesAgentes) == agentesEquipo.size()){
                //si tengo todas las respuestas, evaluamos si soy o no el mejor
                try{
//                     agenteAcceso.aceptaEvento(new EventoRecAgte("yaTengoTodasLasConfirmaciones", this.nombreAgente, this.nombreAgente));
                    this.informaraMiAutomata("yaTengoTodasLasConfirmaciones", null);                     
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Tengo todas las confirmaciones", InfoTraza.NivelTraza.debug));

                }catch(Exception e){
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Error al validar la respuesta. "+e, InfoTraza.NivelTraza.error));
                }
                return;
            }
            //reiniciamos el tiempo sin recibir respuesta
            tiempoSinRecibirRespuesta = new HebraMonitorizacion(5000,this.itfUsoPropiadeEsteAgente, "5segSinRespuesta");
            tiempoSinRecibirRespuesta.start();
        }

        //metodo parecido a validaNumrespuestas. De hecho, es identico pero con confirmaciones
        //Recibimos el agente que nos manda la confirmacion
        public void validaNumEvaluaciones(ArrayList paquete) {
            ArrayList p = paquete;
            String eval = (String)p.get(0);
            String fuente = (String) p.get(1);
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Evaluacion Recibida. Fuente: "+fuente+" Nueva Evaluacion: "+eval, InfoTraza.NivelTraza.debug));
            //actualizar las respuestas
            nuevasEvaluacionesAgentes.set(agentesEquipo.indexOf(fuente), eval);

            if(tiempoSinRecibirRespuesta.isAlive())
                tiempoSinRecibirRespuesta.finalizar();
            //si tenemos todas las respuestas, transitar al estado adecuado
            if(numRespuestasRecibidas(nuevasEvaluacionesAgentes) == empates.size()){
                //si tengo todas las respuestas, evaluamos si soy o no el mejor
                try{
                    if(tengoLaMejorEvalNueva(nuevasEvaluacionesAgentes)){
//                        agenteAcceso.aceptaEvento(new EventoRecAgte("yaTengoTodasLasEvaluacionesYVoyYo", this.nombreAgente, this.nombreAgente));
                        this.informaraMiAutomata("yaTengoTodasLasEvaluacionesYVoyYo", null);
                        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Tengo todas las evaluaciones nuevas y soy el mejor", InfoTraza.NivelTraza.debug));
                    }
                    else{
//                        agenteAcceso.aceptaEvento(new EventoRecAgte("yaTengoTodasLasEvaluacionesYNoVoyYo", this.nombreAgente, this.nombreAgente));
                       this.informaraMiAutomata("yaTengoTodasLasEvaluacionesYNoVoyYo", null);
                        trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Tengo todas las evaluaciones nuevas y no soy el mejor", InfoTraza.NivelTraza.debug));
                    }

                }catch(Exception e){
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Error al validar la respuesta. "+e, InfoTraza.NivelTraza.error));
                }
                return;
            }
            //reiniciamos el tiempo sin recibir respuesta
            tiempoSinRecibirRespuesta = new HebraMonitorizacion(5000,this.itfUsoPropiadeEsteAgente, "5segSinRespuesta");
            tiempoSinRecibirRespuesta.start();
        }

        //metodo que envia la nueva evaluacion a los agentes con los que hemos empatado.
        //Parecido a mandar evaluacion a todos
        public void mandarNuevaEvaluacionAEmpatados(){
            try {
                if(tiempoSinRecibirRespuesta.isAlive())
                    tiempoSinRecibirRespuesta.finalizar();
                //mandamos el mensaje a todos los agentes del sistema, menos a los gestores
                String aux;
//                ArrayList paquete = new ArrayList();
                aux =this.nombreAgente.substring(nombreAgente.length()-1); //nos quedamos con el numero del nombre del agente, que esta al final
                mi_eval_nueva = Integer.parseInt(aux);
//                paquete.add(aux);
//                paquete.add(this.nombreAgente);
                miEvaluacion.valorEvaluacion = mi_eval_nueva;
                for(int i = 0; i< empates.size(); i++){
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Se manda 1 mensaje nuevaEvaluacion a "+ (String) agentesEquipo.get((Integer)empates.get(i)), InfoTraza.NivelTraza.debug));
//                    this.mandaMensajeAAgenteId("nuevaEvaluacion",paquete, (String) agentesEquipo.get((Integer)empates.get(i)));
                    comunicacion.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo("nuevaEvaluacion", miEvaluacion), (String) agentesEquipo.get((Integer)empates.get(i)));
                }
                tiempoSinRecibirRespuesta = new HebraMonitorizacion(5000,this.itfUsoPropiadeEsteAgente, "5segSinRespuesta");
                tiempoSinRecibirRespuesta.start();
            } catch (Exception ex) {
                Logger.getLogger(AccionesSemanticasAgenteAplicacionrobotMasterIA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void reenviarDatos(String agente){
            //reenviar los datos de mi evaluacion a la interfaz del agente que me pasan
//            String aux;
//            ArrayList paquete = new ArrayList();
//            aux =""+ mi_eval;
//            paquete.add(aux);
//            paquete.add(this.nombreAgente);
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Reenviamos evaluacion al agente "+agente, InfoTraza.NivelTraza.debug));
//            mandaMensajeAAgenteId("respuesta",paquete, agente);
             comunicacion.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo("respuesta", miEvaluacion), agente);

        }

        public void reenviarConfirmacion(String agente){
            //reenviar los datos de mi evaluacion a la interfaz del agente que me pasan
            //Primero comprobamos que es el mejor
            if(!this.dameMejor(respuestasAgentes).equals(agente)){
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "El agente que pide la confirmacion "+agente+" NO es el mejor", InfoTraza.NivelTraza.debug));
                return;
            }
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Reenviamos confirmación al agente "+agente, InfoTraza.NivelTraza.debug));
//            mandaMensajeAAgenteId("confirmacion",nombreAgente, agente);
            comunicacion.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo("respuesta",nombreAgente), agente);
        }

        public void reenviarNuevaEvaluacion(String agente){
            //analogo a los dos anteriores, pero con la nueva evaluacion
            //reenviar los datos de mi evaluacion a la interfaz del agente que me pasan
//            String aux;
//            ArrayList paquete = new ArrayList();
//            aux =""+mi_eval_nueva; //nos quedamos con el numero del nombre del agente, que esta al final
//            paquete.add(aux);
//            paquete.add(this.nombreAgente);
            miEvaluacion.valorEvaluacion = mi_eval_nueva;
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Reenviamos evaluacion al agente "+agente+ "Valor : "+mi_eval_nueva, InfoTraza.NivelTraza.debug));
//            mandaMensajeAAgenteId("nuevaEvaluacion",paquete, agente);
            comunicacion.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo("nuevaEvaluacion", miEvaluacion), agente);
        }
	public void terminacion() {
            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                    "Terminando agente: "+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+nombreAgente,
				InfoTraza.NivelTraza.debug));

	}

	public void clasificaError(){
	/*
	 *A partir de esta funci�n se debe decidir si el sistema se puede recuperar del error o no.
	 *En este caso la pol�tica es que todos los errores son cr�ticos.
	 */
		try {
			agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			agenteAcceso.aceptaEvento(new EventoRecAgte("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

		}
		catch (Exception e) {
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
				"Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso",
					InfoTraza.NivelTraza.error));

		}
	}
	public void pedirTerminacionGestorAgentes(){
		try {
			 this.itfUsoGestorAReportar.aceptaMensaje(new MensajeSimple (new InfoContEvtMsgAgteReactivo("peticion_terminar_todo"), this.nombreAgente,NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
		} catch (Exception e) {
			logger.error("Error al mandar el evento de terminar_todo",e);

			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                              "Error al mandar el evento de terminar_todo",
					InfoTraza.NivelTraza.error));
                        this.informaraMiAutomata("error", null);


			}
	}

        public void mandaMensajeAAgenteId(String input, Object infoComplementaria,String IdentAgenteReceptor){

            // Este método crea un evento con la información de entrada y se le envía al agente REACTIVO que se indique por medio de
            // la  interfaz de uso
            EventoRecAgte eventoaEnviar = null;
            ItfUsoAgenteReactivo itfUsoAgenteReceptor = null;

            // Se verifica que la interfaz del aegente no es vacia

            try {
                  itfUsoAgenteReceptor = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
                    (NombresPredefinidos.ITF_USO+IdentAgenteReceptor);
               }
               catch (Exception e) {
    			logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor);
    			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
				"Ha habido un problema enviar el evento con informacion "+ input+"al   agente "+IdentAgenteReceptor,
					  InfoTraza.NivelTraza.error));
               }


            // En primer lugar se crea el evento con  la informacion de entrada
            if (infoComplementaria == null){
                eventoaEnviar = new EventoRecAgte(input, nombreAgente, IdentAgenteReceptor);
             }
            else{eventoaEnviar = new EventoRecAgte(input,infoComplementaria, nombreAgente, IdentAgenteReceptor);}
             // Obtener la interfaz de uso del agente reactivo con el que se quiere comunicar
             try {
			itfUsoAgenteReceptor.aceptaEvento(eventoaEnviar);
            }catch (Exception e) {
		logger.error("Ha habido un problema enviar un  evento al agente "+IdentAgenteReceptor);
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
			  "Ha habido un problema enviar el evento con informacion "+ input+"al   agente "+IdentAgenteReceptor,
                        	  InfoTraza.NivelTraza.error));
            }

    }
 /*       public void mandaMensajeAAgenteItf(String input, Object infoComplementaria,ItfUsoAgenteReactivo itfUsoAgenteReceptor){
            // Este método crea un evento con la información de entrada y se le envía al agente REACTIVO que se indique por medio de
            // la  interfaz de uso
            EventoRecAgte eventoaEnviar = null;

            if (itfUsoAgenteReceptor == null){
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
			  "La interfaz a la que se quiere enviar el mensaje es nula ",
                        	  InfoTraza.NivelTraza.error));
                return;
            }
            // En primer lugar se crea el evento con  la informacion de entrada
            if (infoComplementaria == null){
                eventoaEnviar = new EventoRecAgte(input, nombreAgente, itfUsoAgenteReceptor.toString());
             }
            else{eventoaEnviar = new EventoRecAgte(input,infoComplementaria, nombreAgente, itfUsoAgenteReceptor.toString());}
             // Obtener la interfaz de uso del agente reactivo con el que se quiere comunicar
             try {
			itfUsoAgenteReceptor.aceptaEvento(eventoaEnviar);
            }catch (Exception e) {
		logger.error("Ha habido un problema enviar un  evento al agente "+itfUsoAgenteReceptor.toString());
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
			  "Ha habido un problema enviar el evento con informacion "+ input+"al   agente "+itfUsoAgenteReceptor.toString(),
                        	  InfoTraza.NivelTraza.error));
            }
        }*/

        //en la evaluacion va el Agente que lo ha enviado y la evaluacion en un ArrayList
        private void mandaMensajeATodos(String mensaje, Integer evaluacion){
            try {
                //mandamos el mensaje a todos los agentes del sistema, menos a los gestores
                String aux;
                ArrayList paquete = new ArrayList();
                aux =""+ evaluacion;
                paquete.add(aux);
                paquete.add(this.nombreAgente);
                for(int i = 0; i< agentesEquipo.size(); i++){
                    trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente, "Se manda 1 mensaje " + mensaje+ " a "+ (String)agentesEquipo.get(i), InfoTraza.NivelTraza.debug));
                    this.mandaMensajeAAgenteId(mensaje,paquete, (String)agentesEquipo.get(i) );
                }
            } catch (Exception ex) {
                Logger.getLogger(AccionesSemanticasAgenteAplicacionrobotMasterIA.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
}