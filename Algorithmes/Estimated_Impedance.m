function [Z_eq] = Estimated_Impedance(R,L,C,f)
% Estimated_Impedance Uses the estimated components from the
% Impedance_estimation method. These componenets are then used to construct
% the equivelant estimated impedance
%
% Input : 
% R : Column vector of the resistive componenets 
% L : Column vector of the inductive components 
% C : Column vector of the capacitive components
% f : the frequency interval
%
% Output:
% Z_eq : the estimated equivalent impedance



% 1 - Extract the first resitance
% -------------------------------
Z_eq     = R(1);
n        = length(R);
nC       = length(C);


for i = 2 : nC    
    % 2 - Check if the component is a capcitor
    % -----------------------------------------
    if L(i) == 0
        Z_C      = -1i ./ ( 2*pi*f*C(i));
        Z_Rc     = R(i);
        Z_Ctotal =  Z_Rc + Z_C;
        Z_eq     = 1./ ( 1./Z_eq + 1./Z_Ctotal);
        continue
    end
    
    % 3 - Check if the component is an induction
    % ------------------------------------------
    if C(i) == 0 
        Z_L      = 1i*2*pi*f*L(i);
        Z_Rl     = R(i);
        Z_Ltotal = 1./ ( 1./Z_L + 1./Z_Rl );
        Z_eq = Z_eq + Z_Ltotal;
        continue
    end               
end

