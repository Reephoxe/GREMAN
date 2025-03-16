function Z_Model   = New_Model2(Impe,Z_Model,Z_real,f,r,LorC)
% Compute the new model. This is the model that comes after computing L or C
% so it is the old model until the change point. After the change point we
% CASCADE a capacitor or inductor impedance.
% The new model is the old model cascaded wit the old one. It's a matrix combination and not 'electrical'
% 1 - 'Impe'    : The value of L or C
% 2 - 'Z_Model' : The old model
% 3 - 'f'       : The frequency domain
% 4 - 'r'       : The change instant
% 5 - 'LorC'    : if LorC = 'L' we add an inductor
%                 if LorC = 'C' we add a capacitor
w = 2*pi*f;
% 1 - Find the expression of the module of the cell impedance
% -----------------------------------------------------------

if LorC == 'C' 
    % If the impedance of the cell is capacitive
    % ------------------------------------------
    add_Z = 1./ ( 1i*Impe * 2 * pi * f (r:end));
    
    % This condition applies only if we are starting at the first sample
    % ------------------------------------------------------------------
    if(abs(add_Z(1)) > Z_Model(1)) && (r == 1)
        h      = Z_Model(1);
        Impe_n   = 1/(h*w(1));
        add_Z2 = 1./ ( 1i*Impe_n * 2 * pi * f (r:end));
    else
        add_Z2 = add_Z;
    end

else 
    % If the impedance of the cell is inductive
    % ------------------------------------------    
    add_Z = 1i*Impe * 2 * pi * f (r:end);
    
    % This condition applies only if we are starting at the first sample
    % ------------------------------------------------------------------
    if(abs(add_Z(1)) < Z_Model(1)) && (r == 1)
        h      = Z_Model(1);
        Impe_n = h/w(1);
        add_Z2 = 1i*Impe_n * 2 * pi * f (r:end);
    else
        add_Z2 = add_Z;
    end
end
% 2 - Construct the new model
% ---------------------------
scale1 = Z_real(r)/abs(add_Z(1));
% scale2 = Z_real(r+2)/abs(add_Z(3));
% scale3 = Z_real(r+4)/abs(add_Z(5));
% scale4 = Z_real(r+6)/abs(add_Z(7));
scale0 = (scale1)/1;
if r == 1 % If the starting point is 1
    Z_Model = abs(add_Z2); % Then the new model is the impedance of the first cell           
else           
    Z_Model = [Z_Model(1:r-1)   abs(add_Z)*scale0] ;                      
end
loglog(f,Z_real,'b'); hold on
loglog(f(r:end),abs(add_Z),'r');
loglog(f(r:end),abs(add_Z)*scale0,'k');
grid on
end