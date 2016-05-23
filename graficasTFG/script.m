function [] = script()



files = glob('./*.txt');
for i=1:numel(files)
[~,file_name] = fileparts(files{i});
matrices{i} = readFile(file_name);
files_name{i} = file_name;
endfor

files_name

tiempoMedioSimulacion = 0;
tiempoMaxSimulacion = 0;
tiempoMinSimulacion = 10000000;
for i=1:length(matrices)
    tiempoMedioSimulacion += max(matrices{i}(:,3));
    tiempoMaxSimulacion = max(max(matrices{i}(:,3)),tiempoMaxSimulacion);
    tiempoMinSimulacion = min(max(matrices{i}(:,3)),tiempoMinSimulacion);
endfor
tiempoMedioSimulacion  /= length(matrices); 

printf("El tiempo medio de un rescate es: %f\n",tiempoMedioSimulacion)
printf("El tiempo maximo de un rescate es: %f\n",tiempoMaxSimulacion)
printf("El tiempo minimo de un rescate es: %f\n",tiempoMinSimulacion)










tiempoMedioSalvamento =0;
tiempoMedio1Explorador = 0;
tiempoMedio2Exploradores = 0;
tiempoMedioSinExploradores = 0;

for i=1:length(matrices)

  tiemposAux = matrices{i}(:,3) - matrices{i}(:,2);
  mediaSalvamento = mean(tiemposAux);
  tiempoMedioSalvamento += mediaSalvamento;

  if mod(i,3) == 1
    tiempoMedio1Explorador += mediaSalvamento;
  elseif mod(i,3) == 2
    tiempoMedio2Exploradores += mediaSalvamento;
  elseif mod(i,3) == 0
    tiempoMedioSinExploradores += mediaSalvamento;
  endif



endfor
tiempoMedioSalvamento /= length(matrices);
tiempoMedio1Explorador /= (length(matrices)/3);
tiempoMedio2Exploradores /=(length(matrices)/3);
tiempoMedioSinExploradores /= (length(matrices)/3);
figure(1);
plot([1,2,3,4],[tiempoMedio1Explorador,tiempoMedio2Exploradores,tiempoMedioSinExploradores,tiempoMedioSalvamento]);
title('Tiempos Medios Salvamento');
ylabel('Tiempo(ms)');
set(gca,'xtick',[1,2,3,4])
set(gca,'xticklabel',{'1Explorador','2Exploradores','SinExploradores','Global'});



porcentajeSalvamento = 0;
porcentaje1Explorador = 0;
porcentaje2Exploradores = 0;
porcentajeSinExploradores = 0;

for i=1:length(matrices)

  salvadas = matrices{i}(:,4);
  porcentajeSalvadas = sum(salvadas)/rows(matrices{i});
  porcentajeSalvamento += porcentajeSalvadas;

  if mod(i,3) == 1
    porcentaje1Explorador += porcentajeSalvadas;
  elseif mod(i,3) == 2
    porcentaje2Exploradores += porcentajeSalvadas;
  elseif mod(i,3) == 0
    porcentajeSinExploradores += porcentajeSalvadas;
  endif

endfor
porcentajeSalvamento /= length(matrices);
porcentaje1Explorador /= (length(matrices)/3);
porcentaje2Exploradores /=(length(matrices)/3);
porcentajeSinExploradores /= (length(matrices)/3);
figure(2);
plot([1,2,3,4],[porcentaje1Explorador,porcentaje2Exploradores,porcentajeSinExploradores,porcentajeSalvamento]);
title('% de Victimas Salvadas');
ylabel('(%)');
set(gca,'xtick',[1,2,3,4])
set(gca,'xticklabel',{'1Explorador','2Exploradores','SinExploradores','Global'});



duracionSimulacion = 0;
duracion1Explorador = 0;
duracion2Exploradores = 0;
duracionSinExploradores = 0;

for i=1:length(matrices)

  duracion = max(matrices{i}(:,3));
  %porcentajeSalvadas = sum(salvadas)/rows(matrices{i});
  duracionSimulacion += duracion;

  if mod(i,3) == 1
    duracion1Explorador += duracion;
  elseif mod(i,3) == 2
    duracion2Exploradores += duracion;
  elseif mod(i,3) == 0
    duracionSinExploradores += duracion;
  endif

endfor
duracionSimulacion /= length(matrices);
duracion1Explorador /= (length(matrices)/3);
duracion2Exploradores /=(length(matrices)/3);
duracionSinExploradores /= (length(matrices)/3);
figure(3);
plot([1,2,3,4],[duracion1Explorador,duracion2Exploradores,duracionSinExploradores,duracionSimulacion]);
title('Duracion del rescate');
ylabel('Tiempo (ms)');
set(gca,'xtick',[1,2,3,4])
set(gca,'xticklabel',{'1Explorador','2Exploradores','SinExploradores','Global'});










endfunction