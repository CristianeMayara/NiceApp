# Nice

Version 0.1

Sobre
-----

Nice é uma aplicação móvel que consiste em um teste de agradabilidade de ambientes. Foi desenvolvida para o sistema operacional Android.

Características
---------------

A aplicação faz o uso do sensor de GPS para reconhecer o local em que o usuário está executando o aplicativo através de seu dispositivo móvel e mostra alguns dados desse local, como o nome e uma imagem. O usuário então pode dar uma nota em forma de escala, que varia de 1 a 5, que equivalem a: pouco agradável, aceitável, agradável, muito agradável e agradabilíssimo. É possível adicionar um novo local e editar ou remover um local já conhecido pela aplicação.

O APP possui duas telas principais: na primeira, ele mostra dados do local atual e, na segunda, é mostrado a classificação do local em relação às notas já computadas.

Foi utilizada a solução de framework MpOS ([MpOS page] (http://mpos.great.ufc.br/ )) em seu desenvolvimento para a comunicação entre o aplicativo e o servidor. A contabilidade das notas de cada local é feita no servidor, mas pode ser feita localmente.

Contextualização
----------------

Os dispositivos móveis ainda são computacionalmente limitados em relação a um desktop ou notebook. Uma solução para superar essas limitações é o paradigma de Computação Móvel na Nuvem (MCC), que tem o objetivo de fornecer uma gama de serviços equivalentes à nuvem adaptados para a capacidade de dispositivos com recursos limitados.

O MpOS, que significa Multiplatform Offloading System, é uma solução de framework que provê capacidades de offloading em múltiplas plataformas móveis. A solução foi desenvolvida inicialmente para Android e Windows Phone.

O APP Nice utiliza o framework MpOS para realizar a operação de offloading através de um cloudlet.
