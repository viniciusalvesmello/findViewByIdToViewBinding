# Do findViewById para o View Binding

Durante o [Google I/O'19 tomei nota sobre as vantagens do View Binding](https://dev.to/viniciusalvesmello/resumo-view-binding-android-studio-3-6-google-io-19-5em6). Depois deste evento estava ansioso aguardando o lançamento do Android Studio 3.6. Eis que chegou este dia!

Então, me veio em mente, quais são as principais diferenças em relação ao código, das várias formas de comunicar com os componentes da View no Android?

Para tentar demostrar isto, criei um [repositório no GitHub](https://github.com/viniciusalvesmello/findViewByIdToViewBinding) com exemplos básicos utilizando findViewById, Kotlin Synthetic, Data Binding e View Binding.







## Base do projeto

No projeto temos uma [MainActivity](https://github.com/viniciusalvesmello/findViewByIdToViewBinding/blob/master/app/src/main/java/io/github/viniciusalvesmello/findviewbyidtoviewbinding/view/MainActivity.kt) que é responsável por chamar cada Fragment de exemplo (FindViewByIdFragment, KotlinSyntheticFragment, DataBindingFragment e ViewBindingFragmente). Todas as Fragments compartilham a mesma view model [MainViewModel](https://github.com/viniciusalvesmello/findViewByIdToViewBinding/blob/master/app/src/main/java/io/github/viniciusalvesmello/findviewbyidtoviewbinding/viewmodel/MainViewModel.kt).






## findViewById

O primeiro da lista é o findViewById, sendo que ele existe desde as versões iniciais do Android. A principal vantagem do mesmo é não afetar o tempo de compilação. Mas, em contrapartida não é elegante (Verboso) e não é verificado em tempo de compilação, podendo gerar o famoso NullPointerException.

Segue abaixo o código da [FindViewByIdFragment](https://github.com/viniciusalvesmello/findViewByIdToViewBinding/blob/master/app/src/main/java/io/github/viniciusalvesmello/findviewbyidtoviewbinding/view/FindViewByIdFragment.kt):
```Kotlin
package io.github.viniciusalvesmello.findviewbyidtoviewbinding.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.R
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.viewmodel.MainViewModel

class FindViewByIdFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var tvFindViewByIdCounter: AppCompatTextView
    private lateinit var bFindViewByIdIncCounter: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_view_by_id, container, false)

        tvFindViewByIdCounter = view.findViewById(R.id.tvFindViewByIdCounter)
        bFindViewByIdIncCounter = view.findViewById(R.id.bFindViewByIdIncCounter)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initObserver()
    }

    private fun initListener() {
        bFindViewByIdIncCounter.setOnClickListener {
            viewModel.incCounter()
        }
    }

    private fun initObserver() {
        viewModel.counter.observe(viewLifecycleOwner, Observer {
            tvFindViewByIdCounter.text = (it ?: 0).toString()
        })
    }

    companion object {
        fun newInstance() = FindViewByIdFragment()
    }
}
```

Aqui podemos destacar que precisamos criar variáveis para cada componente do nosso layout, conforme abaixo:

```Kotlin
private lateinit var tvFindViewByIdCounter: AppCompatTextView
private lateinit var bFindViewByIdIncCounter: AppCompatButton
```

E durante a criação da tela, inicializamos as variáveis utilizando o findViewById, passando o id do componente da View do Android, conforme abaixo:

```Kotlin
tvFindViewByIdCounter = view.findViewById(R.id.tvFindViewByIdCounter)
bFindViewByIdIncCounter = view.findViewById(R.id.bFindViewByIdIncCounter)
```






## Kotlin Synthetic

Particularmente o Kotlin Synthetic é o meu preferido, pois além de elegante não impacta o tempo de compilação. Entretanto, o mesmo não é verificado em tempo de compilação, assim como o findViewById, pode gerar o famoso NullPointerException.


Segue abaixo o código da [KotlinSyntheticFragment](https://github.com/viniciusalvesmello/findViewByIdToViewBinding/blob/master/app/src/main/java/io/github/viniciusalvesmello/findviewbyidtoviewbinding/view/KotlinSyntheticFragment.kt):
```Kotlin
package io.github.viniciusalvesmello.findviewbyidtoviewbinding.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.R
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_kotin_synthetic.*

class KotlinSyntheticFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_kotin_synthetic, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initObserver()
    }

    private fun initListener() {
        bKotlinSyntheticCounter.setOnClickListener {
            viewModel.incCounter()
        }
    }

    private fun initObserver() {
        viewModel.counter.observe(viewLifecycleOwner, Observer {
            tvKotlinSyntheticCounter.text = (it ?: 0).toString()
        })
    }

    companion object {
        fun newInstance() = KotlinSyntheticFragment()
    }
}
```

Aqui podemos destacar o import padrão do Kotlin Synthetic, conforme abaixo. Mas não há necessidade de decorar, pois o Android Studio importa automaticamente para você.

```Kotlin
import kotlinx.android.synthetic.main.fragment_kotin_synthetic.*
```

Apenas com este import ja é possível acessar os componentes da view diretamente, conforme abaixo:

```Kotlin
bKotlinSyntheticCounter.setOnClickListener {
    viewModel.incCounter()
}
```





## Data Binding

O Data Binding abre um leque de possibilidades, sendo que, não seria possível demonstrar em um exemplo básico. Por isto sugiro verificar a [documentação oficial](https://developer.android.com/topic/libraries/data-binding). 

Mas qual a vantagem e desvantagem de se utilizar o Data Binding? Primeiramente ele é elegante e consegue tranferir boa parte da lógica da sua activity/fragment, sendo, ao mesmo tempo verificada em tempo de compilação (Adeus NullPointerException). Apesar das grandes vantagens, o Data Binding afeta o tempo de compilação.


Segue abaixo o código da [DataBindingFragment](https://github.com/viniciusalvesmello/findViewByIdToViewBinding/blob/master/app/src/main/java/io/github/viniciusalvesmello/findviewbyidtoviewbinding/view/DataBindingFragment.kt):
```Kotlin
package io.github.viniciusalvesmello.findviewbyidtoviewbinding.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.databinding.FragmentDataBindingBinding
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.viewmodel.MainViewModel

class DataBindingFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentDataBindingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataBindingBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = mainViewModel
        }
        return binding.root
    }

    companion object {
        fun newInstance() = DataBindingFragment()
    }
}
```

Um ponto importante a ser observado é que a forma de inflar o xml muda completamente, pois para cada xml é gerada uma classe com final Binding. No exemplo acima o xml é o `fragment_data_binding.xml`, sendo que, foi gerada a classe `FragmentDataBindingBinding`.

A partir daí você pode me questionar: "Cadê a lógica do click no botão e da atualização do contador de cliques?" Então, com Data Binding podemos fazer isto diretamente no xml do layout. Segue abaixo o código da [fragment_data_binding.xml](https://github.com/viniciusalvesmello/findViewByIdToViewBinding/blob/master/app/src/main/res/layout/fragment_data_binding.xml):
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <variable
            name="viewmodel"
            type="io.github.viniciusalvesmello.findviewbyidtoviewbinding.viewmodel.MainViewModel" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.appcompat.widget.AppCompatTextView
            android:id="@+id/tvDataBindingCounter"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:text="@{Integer.toString(viewmodel.counter), default = 0}"
            android:textSize="60sp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <androidx.appcompat.widget.AppCompatButton
            android:id="@+id/bDataBindingIncCounter"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_marginStart="16dp"
            android:layout_marginTop="16dp"
            android:layout_marginEnd="16dp"
            android:text="Inc with Data Binding"
            android:textAllCaps="false"
            android:onClick="@{() -> viewmodel.incCounter()}"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvDataBindingCounter" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```


Aqui podemos destacar a parte do xml:

- Tag layout:

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

</layout>
```

- Tag data (Origem dos dados):

```xml
<data>
   <variable
       name="viewmodel"
       type="io.github.viniciusalvesmello.findviewbyidtoviewbinding.viewmodel.MainViewModel" />
</data>
```

Logo na Fragment precisamos apenas definir o lifecycleOwner e as variáveis, que no caso, é nossa View Model.

```Kotlin
_binding = FragmentDataBindingBinding.inflate(inflater, container, false).apply {
   lifecycleOwner = viewLifecycleOwner
   viewmodel = mainViewModel
}
```

Feito isto, podemos acessar diretamente a View Model no xml.

Apenas com o código abaixo, conseguimos receber todas as modificações do LiveData counter da View Model. Ao mesmo tempo, não precisamos chamar o onClickListener na Fragment, podendo utilizar diretamente no xml, conforme pode-se verificar logo a seguir:

- LiveData
```xml
android:text="@{Integer.toString(viewmodel.counter), default = 0}"
```
- onClickListener
```xml
android:onClick="@{() -> viewmodel.incCounter()}"
``` 

## View Binding

Por fim falaremos da cereja do bolo: o View Binding. Este consegue resolver os problemas dos anteriores (findViewById, Kotlin Synthetic e Data Binding), sendo elegante, verificado em tempo de compilação e não afeta o tempo de compilação. Entretanto, considero o View Binding mais verboso que o Kotlin Synthetic.

Assim como o Data Binding, não é possível abordar todas as possíbilidades do View Binding, através de exemplos básicos, por isso sugiro ver a [documentação oficial](https://developer.android.com/topic/libraries/view-binding).

Segue abaixo o código da [ViewBindingFragment](https://github.com/viniciusalvesmello/findViewByIdToViewBinding/blob/master/app/src/main/java/io/github/viniciusalvesmello/findviewbyidtoviewbinding/view/ViewBindingFragment.kt):
```Kotlin
package io.github.viniciusalvesmello.findviewbyidtoviewbinding.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.databinding.FragmentViewBindingBinding
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.viewmodel.MainViewModel

class ViewBindingFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentViewBindingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewBindingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initObserver()
    }

    private fun initListener() {
        binding.bViewBindingCounter.setOnClickListener {
            viewModel.incCounter()
        }
    }

    private fun initObserver() {
        viewModel.counter.observe(viewLifecycleOwner, Observer {
            binding.tvViewBindingCounter.text = (it ?: 0).toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ViewBindingFragment()
    }
}
```

O que podemos destacar é que o View Binding utiliza o mesmo conceito do Data Binding, gerando uma classe para o arquivo xml. Ou seja, a partir do xml `fragment_view_binding.xml` foi gerada a classe `FragmentViewBindingBinding`.

A única diferença de utilização do View Binding em relação ao Kotlin Synthetic é que precisamos colocar a variável `binding` (Não precisa ser este nome) antes do id da view.

Para finalizar, não podemos esquecer de destruir a instância do xml, conforme abaixo:

```Kotlin
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
```

## Conclusão

Espero realmente que este artigo e repositório possam ajudar alguém que esteja em dúvida em qual método utilizar. 

Assim como na vida, na programação podemos escolher vários caminhos, sendo que, você deve escolher qual o melhor caminho para você ou seu time.

Mas, realmente, considero que não podemos fechar os olhos para as vantagens do View Binding.
