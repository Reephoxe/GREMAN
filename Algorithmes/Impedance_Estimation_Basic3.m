function [R,L,C] = Impedance_Estimation_Basic3(f , Z_complex , nb_C, nb_L, Coef_C, Coef_L)
  Z  = abs(Z_complex);
  C=[0];
  L=[0];
  i=1;
  a=i;
  borne = -10000;

  while ((Z(i)==0) || (Z(i)==inf))
    i++;
  endwhile
  R=[Z(i)];

  % Parcours des données d'entrée
  % """""""""""""""""""""""""""""
  while (i<(length(Z)-1))

    % Le cas d'une pente décroissante
    % """""""""""""""""""""""""""""""
    if((Z(i) > Z(i+1)) )
      while ((Z(i)> Z(i+1))&& ((i<(length(Z)-1)))&&(Z(i)>borne))
        i++;
      endwhile
      b=i;
      j=0;
      while (mod(b-a-j,nb_C)!=0) %%&& ((i<(length(Z)-1))))
        j++;
      endwhile
      % Ajout de nb_C condensateurs
      % """""""""""""""""""""""""""
      for k =1:(nb_C-1)
        c=  1/((Z((b-a-j)*k/nb_C))*2*Coef_C*pi*(f((b-a-j)*k/nb_C)))
          C=[C; c]
          L=[L;0]
          R= [R ; Z((b-a-j)*k/nb_C)]
      endfor
      c= 1/((Z(b))*2*Coef_C*pi*(f(b)))
      C=[C; c]
      L=[L ;0];
      R= [R; Z(b)]
      i++;
      while ((i<length(Z)-1)&&(Z(i)<borne))
        i++;
      endwhile
      a=i;

    % Le cas d'une pente décroissante
    % """""""""""""""""""""""""""""""
    elseif((Z(i) < Z(i+1)) )
      while ((Z(i)< Z(i+1))&&(i<length(Z)-1)&&(Z(i)>borne))
        i++;
      endwhile
      b=i;
      j=0;
      % Ajout de nb_L inductances
      % """""""""""""""""""""""""
      while (mod(b-a-j,nb_L)!=0)
        j++;
      endwhile
      for k =1:(nb_L-1)
          l= ((Z((b-a-j)*k/nb_L))/(2*Coef_L*pi*(f((b-a-j)*k/nb_L))))
          L=[L; l]
          C=[C;0]
          R= [R ; Z((b-a-j)*k/nb_L)]
      endfor
      l= ((Z(b))/(2*Coef_L*pi*(f(b))))
      L=[L; l]
      C=[C;0]
      R= [R ; Z(b)]
      i++;
      while ((i<length(Z)-1)&&(Z(i)<borne))
        i++;
      endwhile
      a=i;
    endif
  endwhile
end
