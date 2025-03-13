function [Z_Model,R1,i,condi] = New_Model_Cell2 ( Z_Model , Z_real , r , Th_up , Th_dw)
% Compute the new model after adding the entire cell ( LorC + R). The function 'New_Model'
% Only adds the LorC. This one adds LorC with its corresponding R.
% Input : 
% =======
% 1 - 'Z_Model' : The module of the current estimated model
% 2 - 'Z_real'  : The module of the real impedance
% 3 - 'r'       : the change point where we added the LorC
% 4 - 'Th_up'   : The upper threshold for the change detection
% 5 - 'Th_dw'   : The lower threshold for the change detection
%
% Output :
% =======
% 1 - 'Z_Model' : the new model after adding the Entire cell
% 2 - 'R1'      : The value of R of the cell
% 3 - 'i'       : The instant where the new model crosses one of the
%                 thresholds
% 4 - 'condi'   : Some condition to exit the while loop
%==========================================================================

    % Continue and breaking conditions
    % --------------------------------
    condi = 1;
    % 1 - You added the new L or C, Find where the new model satisfy the
    % boundry conditions : this is where you compute the resistance of the
    % cell
    % --------------------------------------------------------------------
    Ratio = Z_real(r:end) ./ Z_Model(r:end);
    a1    = find ( Ratio > Th_up)    ;
    b1    = find ( Ratio < Th_dw)  ;
    if (isempty (a1)) && isempty((b1))
        
        R1    = Z_real(end);
        condi = 0;
        i  = length(Z_real);
        i1 = inf;
        i2 = inf;
        
    elseif ( isempty (b1) && ~isempty(a1) )
        
        i1 = a1(1)+(r)-1;
        i2 = inf;
        
    elseif ( isempty (a1) && ~isempty(b1) )
        
        i1 = inf;
        i2 = b1(1)+(r)-1;
        
    else
        
        i1 = a1(1)+(r)-1;
        i2 = b1(1)+(r)-1;
        
    end

    if ( i1 < i2 ) && (condi ~=0)
        
        Z_Model  = [Z_Model(1:i1)   Z_Model(i1)*ones(1, length(Z_real(i1+1:end)))];
        R1 = Z_real(i1);
        i  = i1+1;
        condi = 1;
        
    end

    if ( i2 < i1 ) && (condi ~=0)
        
        Z_Model  = [Z_Model(1:i2)   Z_Model(i2)*ones(1, length(Z_real(i2+1:end)))];
        R1       = Z_real(i2);
        i = i2+1;
        condi = 1;
        
    end
end