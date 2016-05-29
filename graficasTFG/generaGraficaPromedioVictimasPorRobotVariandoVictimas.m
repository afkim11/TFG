function [] = generaGraficaPromedioVictimasPorRobotVariandoVictimas(escenarios,numFigure,numRobots,tipoEscenario,nvictimas1,nvictimas2,nvictimas3)
indices = [1,2,3,4];
escenariosSeleccionados = {};
j=1;
for i=1:length(escenarios)
  if escenarios{i}{1} == numRobots && escenarios{i}{4} == tipoEscenario && (escenarios{i}{2} == nvictimas1 || escenarios{i}{2} == nvictimas2 || escenarios{i}{2} == nvictimas3)
    escenariosSeleccionados{j} = escenarios{i};
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
for i=1:length(escenariosSeleccionados)
  if escenariosSeleccionados{i}{3} == 1
    if escenariosSeleccionados{i}{2} == nvictimas1
      p14 = [p14 escenariosSeleccionados{i}{2}/length(unique(escenariosSeleccionados{i}{5}(:,5)))];
    elseif escenariosSeleccionados{i}{2} == nvictimas2
      p16 = [p16 escenariosSeleccionados{i}{2}/length(unique(escenariosSeleccionados{i}{5}(:,5)))];
    elseif escenariosSeleccionados{i}{2} == nvictimas3
      p18 = [p18 escenariosSeleccionados{i}{2}/length(unique(escenariosSeleccionados{i}{5}(:,5)))];
    endif
  elseif escenariosSeleccionados{i}{3} == 2
    if escenariosSeleccionados{i}{2} == nvictimas1
      p24 = [p24 escenariosSeleccionados{i}{2}/length(unique(escenariosSeleccionados{i}{5}(:,5)))];
    elseif escenariosSeleccionados{i}{2} == nvictimas2
      p26 = [p26 escenariosSeleccionados{i}{2}/length(unique(escenariosSeleccionados{i}{5}(:,5)))];
    elseif escenariosSeleccionados{i}{2} == nvictimas3
      p28 = [p28 escenariosSeleccionados{i}{2}/length(unique(escenariosSeleccionados{i}{5}(:,5)))];
    endif
  elseif escenariosSeleccionados{i}{3} == 3
    if escenariosSeleccionados{i}{2} == nvictimas1
      p34 = [p34 escenariosSeleccionados{i}{2}/length(unique(escenariosSeleccionados{i}{5}(:,5)))];
    elseif escenariosSeleccionados{i}{2} == nvictimas2
      p36 = [p36 escenariosSeleccionados{i}{2}/length(unique(escenariosSeleccionados{i}{5}(:,5)))];
    elseif escenariosSeleccionados{i}{2} == nvictimas3
      p38 = [p38 escenariosSeleccionados{i}{2}/length(unique(escenariosSeleccionados{i}{5}(:,5)))];
    endif
  endif
endfor

pglobal1 = mean([mean(p14),mean(p24),mean(p34)]);
pglobal2 = mean([mean(p16),mean(p26),mean(p36)]);
pglobal3 = mean([mean(p18),mean(p28),mean(p38)]);
figure(numFigure);
plot(indices,[mean(p14) mean(p24) mean(p34) pglobal1], "b",indices,[mean(p16) mean(p26) mean(p36) pglobal2], "r",indices,[mean(p18) mean(p28) mean(p38) pglobal3], "g");
if tipoEscenario == 1
 nombreEscenario = 'CASA';
elseif tipoEscenario == 2
 nombreEscenario = 'LLANURA';
elseif tipoEscenario == 3
 nombreEscenario = 'ESTADIO';
endif
title(strcat('Promedio victimas por robot ',nombreEscenario,'-',num2str(numRobots),' Robots'));
xlabel('Modos de rescate');
ylabel('victimas/Robot');
legend(strcat(num2str(nvictimas1),' Victimas'),strcat(num2str(nvictimas2),' Victimas'),strcat(num2str(nvictimas3),' Victimas'));
%axis([1,5,0.5,1.01]);
set(gca,'xtick',indices);
set(gca,'xticklabel',{'1Explorador','2Exploradores','SinExploradores','Global'});
%clf();
%surf(peaks);
saveas(numFigure,strcat('PromedioVictimasPorRobotVariandoVictimas',nombreEscenario,num2str(numRobots),'.jpg'));

endfunction