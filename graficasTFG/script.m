function [] = script()
indices = [1,2,3,4];


files = glob('./*.txt');
for i=1:numel(files)
[~,file_name] = fileparts(files{i});
escenarios{i} = readFile(file_name);
files_name{i} = file_name;
endfor

files_name

j=1;
%Grafica CASA, 10 victimas, con 4, 6 y 8 robots.

%Primero recopilo los escenarios que queremos y los guardamos en escenariosCasa
for i=1:length(escenarios)
  if escenarios{i}{2} == 10 && escenarios{i}{4} == 1
    escenariosCasa{j} = escenarios{i};
    j++;
  endif
endfor

p14 = [];
p16 = [];
p18 = [];
p24 = [];
p26 = [];
p28 = [];
p34 = [];
p36 = [];
p38 = [];
for i=1:length(escenariosCasa)
  if escenariosCasa{i}{3} == 1
    if escenariosCasa{i}{1} == 4
      p14 = [p14 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    elseif escenariosCasa{i}{1} == 6
      p16 = [p16 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    elseif escenariosCasa{i}{1} == 8
      p18 = [p18 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    endif
  elseif escenariosCasa{i}{3} == 2
   if escenariosCasa{i}{1} == 4
      p24 = [p24 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    elseif escenariosCasa{i}{1} == 6
      p26 = [p26 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    elseif escenariosCasa{i}{1} == 8
      p28 = [p28 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    endif
  elseif escenariosCasa{i}{3} == 3
   if escenariosCasa{i}{1} == 4
      p34 = [p34 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    elseif escenariosCasa{i}{1} == 6
      p36 = [p36 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    elseif escenariosCasa{i}{1} == 8
      p38 = [p38 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    endif
  endif
endfor

pglobal = mean([mean(p14),mean(p16),mean(p18),mean(p24),mean(p26),mean(p28),mean(p34),mean(p36),mean(p38)]);
figure(1);
plot(indices,[mean(p14) mean(p24) mean(p34) pglobal], "b",indices,[mean(p16) mean(p26) mean(p36) pglobal], "r",indices,[mean(p18) mean(p28) mean(p38) pglobal], "g");
title('Porcentaje de Salvamento de victimas CASA-10Victimas');
ylabel('% Victimas Salvadas');
legend('4 Robots','6 Robots','8 Robots');
axis([1,5,0.5,1.01]);
set(gca,'xtick',indices);
set(gca,'xticklabel',{'1Explorador','2Exploradores','SinExploradores','Global'});




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