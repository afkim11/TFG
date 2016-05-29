function [] = generaGraficaPorcentajeVariandoVictimas(escenarios,numFigure,numRobots,tipoEscenario,nvictims1,nvictims2,nvictims3)
indices = [1,2,3,4];
escenariosCasa = {};
j=1;
for i=1:length(escenarios)
  if escenarios{i}{1} == numRobots && escenarios{i}{4} == tipoEscenario && (escenarios{i}{2} == nvictims1 || escenarios{i}{2} == nvictims2 || escenarios{i}{2} == nvictims3)
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
    if escenariosCasa{i}{2} == nvictims1
      p14 = [p14 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    elseif escenariosCasa{i}{2} == nvictims2
      p16 = [p16 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    elseif escenariosCasa{i}{2} == nvictims3
      p18 = [p18 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    endif
  elseif escenariosCasa{i}{3} == 2
    if escenariosCasa{i}{2} == nvictims1
      p24 = [p24 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    elseif escenariosCasa{i}{2} == nvictims2
      p26 = [p26 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    elseif escenariosCasa{i}{2} == nvictims3
      p28 = [p28 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    endif
  elseif escenariosCasa{i}{3} == 3
   if escenariosCasa{i}{2} == nvictims1
      p34 = [p34 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    elseif escenariosCasa{i}{2} == nvictims2
      p36 = [p36 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
    elseif escenariosCasa{i}{2} == nvictims3
      p38 = [p38 sum(escenariosCasa{i}{5}(:,4))/escenariosCasa{i}{2}];
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
title(strcat('Porcentaje de salvamento de victimas ',nombreEscenario,'-',num2str(numRobots),' Robots'));
xlabel('Modos de rescate');
ylabel('% VS');
legend(strcat(num2str(nvictims1),' Victimas'),strcat(num2str(nvictims2),' Victimas'),strcat(num2str(nvictims3),' Victimas'));
axis([1,5,0.5,1.01]);
set(gca,'xtick',indices);
set(gca,'xticklabel',{'1Explorador','2Exploradores','SinExploradores','Global'});
%clf();
%surf(peaks);
saveas(numFigure,strcat('PorcentajeSalvamentoVariandoVictimas',nombreEscenario,num2str(numRobots),'.jpg'));

endfunction