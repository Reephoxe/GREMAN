function [R2,L2,C2] = ScriptTotal(f , Z , nb_C, nb_L, Coef_C, Coef_L)

pkg load optim

% 1 - Basic Estimation
% """"""""""""""""""""

[R,L,C] = Impedance_Estimation_Basic3(f , Z , nb_C, nb_L, Coef_C, Coef_L)

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
x_AllExp = fmincon ( E_AllExp   , initial_Imp2 , [] , [], [] , [] , lb2 , up2);

% 5 - Reconstruct the results
% ---------------------------
[R2, L2 , C2] = RLC_construct ( x_AllExp , initial_cst2, 'Y' , R , L ,C);
