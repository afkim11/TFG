function [] = script()
files = glob('./*.txt');
for i=1:numel(files)
[~,file_name] = fileparts(files{i});
matrices{i} = readFile(file_name);
endfor
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



for i=1:length(matrices)

endfor




endfunction