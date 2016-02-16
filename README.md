# Nice

Version 0.1

Sobre
-----

Nice é uma aplicação móvel que consiste em um teste de agradabilidade de ambientes. Foi desenvolvida para o sistema operacional Android.

Características
---------------

A aplicação faz o uso do sensor de GPS para reconhecer o local em que o usuário está executando o aplicativo através de seu dispositivo móvel e mostra alguns dados desse local, como o nome e uma imagem. O usuário então pode dar uma nota em forma de escala, que varia de 1 a 5, que equivalem a: pouco agradável, aceitável, agradável, muito agradável e agradabilíssimo. É possível adicionar um novo local e editar ou remover um local já conhecido pela aplicação.

Inicialmente o APP reconhece apenas alguns locais conhecidos do interior do Campus do Pici, UFC.

O APP possui duas telas principais: na primeira, ele mostra dados do local atual e, na segunda, é mostrado a classificação do local em relação às notas já computadas.

A contabilidade das notas de cada local é feita no servidor, mas pode ser feita localmente. Foi utilizada a solução de framework MpOS ([MpOS page] (http://mpos.great.ufc.br/ )) em seu desenvolvimento para a comunicação entre o aplicativo e o servidor.

Contextualização
----------------

Os dispositivos móveis ainda são computacionalmente limitados em relação a um desktop ou notebook. Uma solução para superar essas limitações é o paradigma de Computação Móvel na Nuvem (MCC), que tem o objetivo de fornecer uma gama de serviços equivalentes à nuvem adaptados para a capacidade de dispositivos com recursos limitados.

O MpOS, que significa Multiplatform Offloading System, é uma solução de framework que provê capacidades de offloading em múltiplas plataformas móveis. A solução foi desenvolvida inicialmente para Android e Windows Phone.

O APP Nice utiliza o framework MpOS para realizar a operação de offloading através de um cloudlet.

Funcionamento
----------------

A aplicação é executada no dispositivo móvel e, após a inicialização da API do MpOS, o primeiro componente que interage com o servidor é a descoberta de serviço, que tem como objetivo descobrir a presença do cloudlet em uma rede local sem fio. O processo de implantação de serviço é realizado em seguida, no qual há a transferência dos arquivos presentes nas dependências da aplicação para o servidor. O servidor recebe essas informações que representam a aplicação e cria o serviço de offloading.

O sistema de offloading funciona em conjunto com a execução do aplicativo. O framework intercepta a chamada ao método marcado. O tipo de particionamento utilizado foi o estático, assim o sistema verifica se o servidor remoto, o cloudlet, está disponível e, em caso positivo, realiza a operação de offloading. Caso não seja possível executar no servidor, o método é executado localmente no dispositivo móvel.
