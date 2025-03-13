function E = ErrEq(Z , Z_equation)
    Nom   = @(x) abs(Z) - abs(Z_equation(x));            % Numerator of the error
    Denom = @(x) abs(Z) + abs(Z_equation(x));            % Denomenator of the error
    Ratio = @(x) abs( Nom(x) ./ Denom(x) ); 
    E     = @(x) sum ( Ratio (x) );
end