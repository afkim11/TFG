function [] = script()



files = glob('./FicherosResultados/*.txt');
for i=1:numel(files)
[~,file_name] = fileparts(files{i});
escenarios{i} = readFile(file_name);
files_name{i} = file_name;
endfor

files_name



generaGraficaPorcentaje(escenarios,1,10,1,4,6,8);
generaGraficaTiempoMedioVictima(escenarios,2,10,1,4,6,8);
generaGraficaTiempoTotalSimulacion(escenarios,3,10,1,4,6,8);
generaGraficaPromedioVictimasPorRobot(escenarios,4,10,1,4,6,8);


generaGraficaPorcentaje(escenarios,5,6,2,2,4,6);
generaGraficaTiempoMedioVictima(escenarios,6,6,2,2,4,6);
generaGraficaTiempoTotalSimulacion(escenarios,7,6,2,2,4,6);
generaGraficaPromedioVictimasPorRobot(escenarios,8,6,2,2,4,6);

generaGraficaPorcentaje(escenarios,9,14,3,4,6,8);
generaGraficaTiempoMedioVictima(escenarios,10,14,3,4,6,8);
generaGraficaTiempoTotalSimulacion(escenarios,11,14,3,4,6,8);
generaGraficaPromedioVictimasPorRobot(escenarios,12,14,3,4,6,8);


generaGraficaPorcentajeVariandoVictimas(escenarios,13,8,3,14,17,20);
generaGraficaTiempoTotalSimulacionVariandoVictimas(escenarios,14,8,3,14,17,20);





%tiempoMedioSimulacion = 0;
%tiempoMaxSimulacion = 0;
%tiempoMinSimulacion = 10000000;
%for i=1:length(escenarios)
%    tiempoMedioSimulacion += max(escenarios{i}(:,3));
%    tiempoMaxSimulacion = max(max(escenarios{i}(:,3)),tiempoMaxSimulacion);
%    tiempoMinSimulacion = min(max(escenarios{i}(:,3)),tiempoMinSimulacion);
%endfor
%tiempoMedioSimulacion  /= length(escenarios); 
%
%printf("El tiempo medio de un rescate es: %f\n",tiempoMedioSimulacion)
%printf("El tiempo maximo de un rescate es: %f\n",tiempoMaxSimulacion)
%printf("El tiempo minimo de un rescate es: %f\n",tiempoMinSimulacion)
%
%
%
%
%
%
%
%
%
%
%tiempoMedioSalvamento =0;
%tiempoMedio1Explorador = 0;
%tiempoMedio2Exploradores = 0;
%tiempoMedioSinExploradores = 0;
%
%for i=1:length(escenarios)
%
%  tiemposAux = escenarios{i}(:,3) - escenarios{i}(:,2);
%  mediaSalvamento = mean(tiemposAux);
%  tiempoMedioSalvamento += mediaSalvamento;
%
%  if mod(i,3) == 1
%    tiempoMedio1Explorador += mediaSalvamento;
%  elseif mod(i,3) == 2
%    tiempoMedio2Exploradores += mediaSalvamento;
%  elseif mod(i,3) == 0
%    tiempoMedioSinExploradores += mediaSalvamento;
%  endif
%
%
%
%endfor
%tiempoMedioSalvamento /= length(escenarios);
%tiempoMedio1Explorador /= (length(escenarios)/3);
%tiempoMedio2Exploradores /=(length(escenarios)/3);
%tiempoMedioSinExploradores /= (length(escenarios)/3);
%figure(1);
%plot([1,2,3,4],[tiempoMedio1Explorador,tiempoMedio2Exploradores,tiempoMedioSinExploradores,tiempoMedioSalvamento]);
%title('Tiempos Medios Salvamento');
%ylabel('Tiempo(ms)');
%set(gca,'xtick',[1,2,3,4])
%set(gca,'xticklabel',{'1Explorador','2Exploradores','SinExploradores','Global'});
%
%
%
%porcentajeSalvamento = 0;
%porcentaje1Explorador = 0;
%porcentaje2Exploradores = 0;
%porcentajeSinExploradores = 0;
%
%for i=1:length(escenarios)
%
%  salvadas = escenarios{i}(:,4);
%  porcentajeSalvadas = sum(salvadas)/rows(escenarios{i});
%  porcentajeSalvamento += porcentajeSalvadas;
%
%  if mod(i,3) == 1
%    porcentaje1Explorador += porcentajeSalvadas;
%  elseif mod(i,3) == 2
%    porcentaje2Exploradores += porcentajeSalvadas;
%  elseif mod(i,3) == 0
%    porcentajeSinExploradores += porcentajeSalvadas;
%  endif
%
%endfor
%porcentajeSalvamento /= length(escenarios);
%porcentaje1Explorador /= (length(escenarios)/3);
%porcentaje2Exploradores /=(length(escenarios)/3);
%porcentajeSinExploradores /= (length(escenarios)/3);
%figure(2);
%plot([1,2,3,4],[porcentaje1Explorador,porcentaje2Exploradores,porcentajeSinExploradores,porcentajeSalvamento]);
%title('% de Victimas Salvadas');
%ylabel('(%)');
%set(gca,'xtick',[1,2,3,4])
%set(gca,'xticklabel',{'1Explorador','2Exploradores','SinExploradores','Global'});
%
%
%
%duracionSimulacion = 0;
%duracion1Explorador = 0;
%duracion2Exploradores = 0;
%duracionSinExploradores = 0;
%
%for i=1:length(escenarios)
%
%  duracion = max(escenarios{i}(:,3));
%  %porcentajeSalvadas = sum(salvadas)/rows(escenarios{i});
%  duracionSimulacion += duracion;
%
%  if mod(i,3) == 1
%    duracion1Explorador += duracion;
%  elseif mod(i,3) == 2
%    duracion2Exploradores += duracion;
%  elseif mod(i,3) == 0
%    duracionSinExploradores += duracion;
%  endif
%
%endfor
%duracionSimulacion /= length(escenarios);
%duracion1Explorador /= (length(escenarios)/3);
%duracion2Exploradores /=(length(escenarios)/3);
%duracionSinExploradores /= (length(escenarios)/3);
%figure(3);
%plot([1,2,3,4],[duracion1Explorador,duracion2Exploradores,duracionSinExploradores,duracionSimulacion]);
%title('Duracion del rescate');
%ylabel('Tiempo (ms)');
%set(gca,'xtick',[1,2,3,4])
%set(gca,'xticklabel',{'1Explorador','2Exploradores','SinExploradores','Global'});















endfunction