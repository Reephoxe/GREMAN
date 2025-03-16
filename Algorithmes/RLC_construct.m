function [R_new , L_new , C_new] = RLC_construct ( x , initial_cst, Exp_Or_NoExp , R , L ,C)
    n_R = length(R);
    n_C = length(C);
    if n_R > n_C
        R = R(1:n_R-1);
    end

    R_new = [];
    L_new = [];
    C_new = [];
    if isempty(initial_cst)
        initial_cst = 1;
    end
    n = length(C);
    j=1;
    for i = 1 : n_C
        if i == 1
            if Exp_Or_NoExp == 'Y'
              R_new = [R_new ; initial_cst(j) * 10^x(j)];
            else
              R_new = [R_new ; x(j)];
            end
            C_new = [ C_new ; 0];
            L_new = [ L_new ; 0];
            j = j+1;
            continue
        end

        if C(i)~=0
            if Exp_Or_NoExp == 'Y'
                R_new = [R_new ; initial_cst(j) * 10^x(j)];
                C_new = [C_new ; initial_cst(j+1) * 10^x(j+1)];
            else
                R_new = [R_new ; x(j)];
                C_new = [C_new ; x(j+1)];
            end
            L_new = [L_new ; 0];
            j = j+2;
            continue
        end

        if L(i)~=0
            if Exp_Or_NoExp == 'Y'
                R_new = [R_new ; initial_cst(j) * 10^x(j)];
                L_new = [L_new ; initial_cst(j+1) * 10^x(j+1)];
            else
                R_new = [R_new ; x(j)];
                L_new = [L_new ; x(j+1)];
            end
            C_new = [C_new ; 0];
            j = j+2;
            continue
        end

    end
end

