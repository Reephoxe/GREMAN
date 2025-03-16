function Impe = New_LorC2(x_change , f , Z_real , LorC)
% Calculating the value of C at the change point
% Inputs:
% 1 - 'x_change' : the instant of threshold crossing
% 2 - 'f'        : the frequency domain
% 3 - 'Z_real'   : the module of the real impedance

% 5 - 'LorC'     : if LorC = 'C' we use the formula for a capacitor
%                  if LorC = 'L' we use the formula for the inductor

% We take samples to compute the impedance
Z1 = Z_real(x_change);
% Z2 = Z_real(x_change+2);
% Z3 = Z_real(x_change+4);
% Z4 = Z_real(x_change+6);
w1 = 2*pi*f(x_change);
% w2 = 2*pi*f(x_change+2);
% w3 = 2*pi*f(x_change+4);
% w4 = 2*pi*f(x_change+6);

if LorC == 'C'
    % The cell is capacitif
    % ---------------------
     Impe = (1/(w1*Z1));
else
    % The cell is inductive
    % ---------------------
    Impe = (Z1)/(w1);
end    
end    
