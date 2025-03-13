clear
pkg load optim;
tic
filename = fullfile(fileparts(mfilename('fullpath')), 'Z_Lamp.mat');

data = load(filename)
disp(data)
f = Z(:,1)';
Z = Z(:,2)';
Z = Z(30:end);
f = f(30:end);
figure
subplot(211)
loglog(f,abs(Z),'LineWidth',1)
ylabel('Impedance Module [\Omega]');
title( 'Impedance Module')
grid on
set(gca,'FontSize',14);

subplot(212)
semilogx(f,angle(Z)*180/pi,'LineWidth',1);
ylabel('Impedance Phase [Degree]');
xlabel('Frequency [Hz]');
title( 'Impedance Phase')
grid on
set(gca,'FontSize',14);

% 1 - Basic Estimation
% """"""""""""""""""""
cd(fileparts(mfilename('fullpath'))); % Se place dans le dossier du script
addpath(fullfile(pwd, 'version_finale'));
[R,L,C]  = Impedance_Estimation_Basic3(f , Z , 4, 2 ,7 ,4.05 );

% 2 - Equation of the total estimated impedance
% """""""""""""""""""""""""""""""""""""""""""""
[Z_AllExp  , initial_Imp2 , initial_cst2] = Imp_Eq (R , L , C , f , 2);

% 3 - Euqation of the errors
% """"""""""""""""""""""""""
E_AllExp   = ErrEq(Z      , Z_AllExp);

% 4 - Optimization Step
% """""""""""""""""""""
n2  = length(initial_Imp2);
lb2 = -12*ones(1,n2);
up2 = 6*ones(1,n2);
x_AllExp   = fmincon ( E_AllExp   , initial_Imp2 , [] , [], [] , [] , lb2 , up2);

% 5 - Reconstruct the results
% ---------------------------
[R2, L2 , C2] = RLC_construct ( x_AllExp , initial_cst2, 'Y' , R , L ,C);

% R2(1) = R(1);
% 6 - Plot and comparision
% """"""""""""""""""""""""
Z0     = Estimated_Impedance(R,L,C,f);
Z2     = Estimated_Impedance(R2,L2,C2,f);
toc
figure
subplot(211)
loglog(f,abs(Z) ,'m','LineWidth',2);hold on
loglog(f,abs(Z0),'LineWidth',2)
loglog(f,abs(Z2), '--b' , 'LineWidth' , 1)
title ( 'Module of the real - Estimated and optimized impedance')
...
