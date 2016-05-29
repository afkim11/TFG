function [] = generaGraficaPorcentaje(escenarios,numFigure,numVictimas,tipoEscenario,nrobots1,nrobots2,nrobots3)
indices = [1,2,3,4];
escenariosSeleccionados = {};
j=1;
for i=1:length(escenarios)
  if escenarios{i}{2} == numVictimas && escenarios{i}{4} == tipoEscenario
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
    if escenariosSeleccionados{i}{1} == nrobots1
      p14 = [p14 sum(escenariosSeleccionados{i}{5}(:,4))/escenariosSeleccionados{i}{2}];
    elseif escenariosSeleccionados{i}{1} == nrobots2
      p16 = [p16 sum(escenariosSeleccionados{i}{5}(:,4))/escenariosSeleccionados{i}{2}];
    elseif escenariosSeleccionados{i}{1} == nrobots3
      p18 = [p18 sum(escenariosSeleccionados{i}{5}(:,4))/escenariosSeleccionados{i}{2}];
    endif
  elseif escenariosSeleccionados{i}{3} == 2
   if escenariosSeleccionados{i}{1} == nrobots1
      p24 = [p24 sum(escenariosSeleccionados{i}{5}(:,4))/escenariosSeleccionados{i}{2}];
    elseif escenariosSeleccionados{i}{1} == nrobots2
      p26 = [p26 sum(escenariosSeleccionados{i}{5}(:,4))/escenariosSeleccionados{i}{2}];
    elseif escenariosSeleccionados{i}{1} == nrobots3
      p28 = [p28 sum(escenariosSeleccionados{i}{5}(:,4))/escenariosSeleccionados{i}{2}];
    endif
  elseif escenariosSeleccionados{i}{3} == 3
   if escenariosSeleccionados{i}{1} == nrobots1
      p34 = [p34 sum(escenariosSeleccionados{i}{5}(:,4))/escenariosSeleccionados{i}{2}];
    elseif escenariosSeleccionados{i}{1} == nrobots2
      p36 = [p36 sum(escenariosSeleccionados{i}{5}(:,4))/escenariosSeleccionados{i}{2}];
    elseif escenariosSeleccionados{i}{1} == nrobots3
      p38 = [p38 sum(escenariosSeleccionados{i}{5}(:,4))/escenariosSeleccionados{i}{2}];
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
title(strcat('Porcentaje de salvamento de victimas ',nombreEscenario,'-',num2str(numVictimas),' Victimas'));
xlabel('Modos de rescate');
ylabel('% VS');
legend(strcat(num2str(nrobots1),' Robots'),strcat(num2str(nrobots2),' Robots'),strcat(num2str(nrobots3),' Robots'));
axis([1,5,0.5,1.01]);
set(gca,'xtick',indices);
set(gca,'xticklabel',{'1Explorador','2Exploradores','SinExploradores','Global'});
%clf();
%surf(peaks);
saveas(numFigure,strcat('PorcentajeSalvamento',nombreEscenario,num2str(numVictimas),'.jpg'));

endfunction