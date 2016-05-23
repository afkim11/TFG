function [matrix] = readFile(file_name);
victimas = [];
tiemposAsignacion = [];
tiemposResolucion = [];
salvadas = [];
robotsEncargados = [];
fid = fopen(strcat(file_name,".txt"));
if fid != -1
while (!feof (fid) )
vid = fscanf(fid,"%f",1);
at = fscanf(fid,"%f",1);
st = fscanf(fid,"%f",1);
s = fscanf(fid,"%f",1);
r = fscanf(fid,"%f",1);
victimas = [victimas;vid];
tiemposAsignacion = [tiemposAsignacion;at];
tiemposResolucion = [tiemposResolucion;st];
salvadas = [salvadas;s];
robotsEncargados = [robotsEncargados;r];
endwhile
fclose(fid);
else

strcat(file_name,".txt")

endif
matrix = [victimas tiemposAsignacion tiemposResolucion salvadas];

endfunction