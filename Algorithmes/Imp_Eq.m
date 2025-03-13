function [Z_eq,initial_Imp,initial_cst] = Imp_Eq(R , L , C , f , WhichEq)
% Imp_Eq : To use the fmincon, we need a function handle constructed from
% the different initial estimation. This function constructs this handle.
% We choose to choose the function handle based on what we want to
% optimize. We have 'Insert Number Here' variants to optimize:
% 1 - Optimize the entire values of the parameters
% 2 - Optimize the exponents of the parameters
% 3 - Optimize the entire values of the resistors
% 4 - Optimize the exponents of the resistors
% Inputs:
% ------
% 'R' : The initial estimation of the resistors
% 'L' : The initial estimation of the inductors
% 'C' : the initial estimation of the capacitor
% 'f' : the frequency domain
% 'WhichEq' : A constant equal to {1,2,3,4}. The value of this input
% changes the equation of the Z_eq
R = 50.990;
L = 0;
C = 0;
f = 1000;
WhichEq = 2;

w = 2*pi*f;
Z_eq=[];
n_R =length(R);
n_C = length(C);

if n_R > n_C
    R = R(1:n_R-1);
end

nR = length(R);        % Number of resistive components
nC = length(find(C>0));% Number of capacitive components
nL = length(find(L>0));% Number of inductive components
n  = nR + nC + nL;     % Total Number of component
initial_Imp = [];      %Here we save the initial exponents
i=1;
initial_cst = [];



%% 1 - Equation to optimze the entire value of the parameters
% ----------------------------------------------------------
if WhichEq == 1
    for j=1:nR
        initial_Imp = [initial_Imp R(j)];

        % i - Construct the equation of the first resistive cell
        % ******************************************************
        if j == 1 % The first element is a standalone resistor
            Z_eq = @(x) x(i) ; % Equation of the total impedance
            i = i + 1 ;
            continue
        end

        % ii - Construct the equation of the next cell
        % ********************************************
            % a - If the cell is capacitive
            % +++++++++++++++++++++++++++++
            if ( C(j) ~=0 )
                initial_Imp = [initial_Imp C(j)];
                % Equation of the resitive part of the cell
                % .........................................
                Z_R = @(x) x(i);

                % Equation of the capacitive part of the cell
                % ...........................................
                Z_C = @(x) 1./(1i.* w .* x(i+1));

                % Equation of the entire cell
                % ...........................
                Z_cell = @(x) Z_R(x) + Z_C(x);

                % Updating the total impedance Z_eq
                % .................................
                Z_eq = @(x) 1./ ( 1./Z_eq(x) + 1./Z_cell(x));
                i =i+2;

                % Breaking condition
                % ..................
                if i > n
                    break
                end
                continue

            end

            % b - If the cell is inductive
            % ++++++++++++++++++++++++++++
            if ( L(j) ~= 0 )
                initial_Imp = [initial_Imp L(j)];
                % Equation of the resitive part of the cell
                % .........................................
                Z_R = @(x) x(i);

                % Equation of the Inductive part of the cell
                % ...........................................
                Z_L = @(x) (1i.* w .* x(i+1));

                % Equation of the entire cell
                % ...........................
                Z_cell = @(x) 1./ (1./Z_R(x) + 1./Z_L(x));

                % Updating the total impedance Z_eq
                % .................................
                Z_eq = @(x) Z_eq(x) + Z_cell(x);
                i =i+2;

                % Breaking condition
                % ..................
                if i > n
                    break
                end
                continue
            end
    end
end

%% 2 - Equation for the estimation of the parameter exponents
%  ----------------------------------------------------------
if WhichEq == 2
    for j = 1 : nR

        % i - Extract the constant and the exponent from the resisitve part
        % -----------------------------------------------------------------
        eR = ( log10(R(j) )); % Exponent of the resistive part
        cR = R(j) ./ 10.^eR;       % Constant part of the resitive part

        % ii - Update the initial exponents
        % ---------------------------------
        initial_Imp = [ initial_Imp eR];
        initial_cst = [ initial_cst cR];
        % iii - Construct the equation of the equivalent impedance for the
        % first cell
        % ----------------------------------------------------------------
        if i == 1 % The first element is a standalone resistor
            Z_eq = @(x) cR.*10.^x(i); % Equation of the total impedance
            i    = i+1;
            continue
        end

        % iv - Construct the equation of the total imedance for the next cells
        % --------------------------------------------------------------------

        if C(j) ~= 0 % if the current cell is capacitive

            eC = ( log10(C(j) )); % Exponent of the capacitive part of the cell
            cC = C(j)./10.^eC;         % Constant part of the capacitive part
            initial_Imp = [ initial_Imp eC];
            initial_cst = [ initial_cst cC];
            % Equation of the resistor imedance
            % ---------------------------------
            Z_R = @(x) cR.*10.^x(i);

            % Equation of the capacitor impedance
            % -----------------------------------
            C1  = @(x) cC.*10.^x(i+1);
            Z_C = @(x) 1./(1i .* C1(x) .* w);

            % Equation of the cell impedance
            % ------------------------------
            Z_cell = @(x) Z_C(x) + Z_R(x);

            % update the count and exit the loop
            % ----------------------------------
            Z_eq   = @(x) 1./ ( 1./Z_eq(x) + 1./Z_cell(x) );
            i = i+2;

            if i > n
                break
            end
            continue

        elseif L(j) ~= 0 % if the current cell is inductive

            eL = ( log10(L(j) )); % Exponent of the capacitive part of the cell
            cL = L(j)./10.^eL;         % Constant part of the capacitive part
            initial_Imp = [ initial_Imp eL];
            initial_cst = [ initial_cst cL];
            % Equation of the resitive part
            % -----------------------------
            Z_R = @(x) cR.*10.^x(i);

            % Equation of the inductor impedance
            % ----------------------------------
            L1  = @(x) cL.*10.^x(i+1);
            Z_L = @(x) 1i.*L1(x)*w;

            % Equation of the cell impedance
            % ------------------------------
            Z_cell = @(x) 1./ ( 1./Z_R(x) + 1./Z_L(x));

            % Update the count and exit the loop
            % ----------------------------------
            Z_eq = @(x) Z_eq(x) + Z_cell(x);
            i    = i+2;

            if i > n
                break
            end
            continue

        else
        end

    end

end

%% 3 - Equation for the estimation of entire resitive compoents
%  ------------------------------------------------------------
if WhichEq == 3
    for j = 1 : nR

        initial_Imp = [initial_Imp R(j)];

        % i - Construct the equation of the equivalent impedance for the
        % first cell
        % ---------------------------------------------------------------
        if j == 1 % The first element is a standalone resistor
            Z_eq = @(x) x(j) ; % Equation of the total impedance
            continue
        end

        % ii - Construct the equation of the total imedance for the next cells
        % --------------------------------------------------------------------
        if C(j) ~= 0 % if the current cell is capacitive
            Z_R = @(x) x(j);

            % Equation of the capacitor impedance
            % -----------------------------------
            Z_C = 1./(1i*C(j)*w);

            % Equation of the cell impedance
            % ------------------------------
            Z_cell = @(x) Z_C + Z_R(x);

            % update the count and exit the loop
            % ----------------------------------
            Z_eq   = @(x) 1./ ( 1./Z_eq(x) + 1./Z_cell(x) );
            continue

        elseif L(j) ~= 0 % if the current cell is inductive

            % Equation of the resitive part
            % -----------------------------
            Z_R = @(x) x(j);

            % Equation of the inductor impedance
            % ----------------------------------
            Z_L = 1i.*L(j)*w;

            % Equation of the cell impedance
            % ------------------------------
            Z_cell = @(x) 1./ ( 1./Z_R(x) + 1./Z_L);

            % Update the count and exit the loop
            % ----------------------------------
            Z_eq = @(x) Z_eq(x) + Z_cell(x);
            continue
        else

        end

    end
end

%% 4 - Equation to optimize the resistor exponents
%  -----------------------------------------------
if WhichEq == 4
    for j = 1 : nR

        % i - Extract the constant and the exponent from the resisitve part
        % -----------------------------------------------------------------
        eR = floor( log10(R(j) )); % Exponent of the resistive part
        cR = R(j) ./ 10.^eR;       % Constant part of the resitive part

        % ii - Update the initial exponents
        % ---------------------------------
        initial_Imp = [ initial_Imp eR];
        initial_cst = [ initial_cst cR];
        % iii - Construct the equation of the equivalent impedance for the
        % first cell
        % ----------------------------------------------------------------
        if j == 1 % The first element is a standalone resistor
            Z_eq = @(x) cR.*10.^x(j); % Equation of the total impedance
            continue
        end

        % iv - Construct the equation of the total imedance for the next cells
        % --------------------------------------------------------------------

        if C(j) ~= 0 % if the current cell is capacitive

            Z_R = @(x) cR.*10.^x(j);

            % Equation of the capacitor impedance
            % -----------------------------------
            Z_C =  1./(1i .* C(j) .* w);

            % Equation of the cell impedance
            % ------------------------------
            Z_cell = @(x) Z_C + Z_R(x);

            % update the count and exit the loop
            % ----------------------------------
            Z_eq   = @(x) 1./ ( 1./Z_eq(x) + 1./Z_cell(x) );
            continue

        elseif L(j) ~= 0 % if the current cell is inductive

            % Equation of the resitive part
            % -----------------------------
            Z_R = @(x) cR.*10.^x(j);

            % Equation of the inductor impedance
            % ----------------------------------
            Z_L = 1i.*L(j)*w;

            % Equation of the cell impedance
            % ------------------------------
            Z_cell = @(x) 1./ ( 1./Z_R(x) + 1./Z_L);

            % Update the count and exit the loop
            % ----------------------------------
            Z_eq = @(x) Z_eq(x) + Z_cell(x);
            continue
        else
        end
    end
end



