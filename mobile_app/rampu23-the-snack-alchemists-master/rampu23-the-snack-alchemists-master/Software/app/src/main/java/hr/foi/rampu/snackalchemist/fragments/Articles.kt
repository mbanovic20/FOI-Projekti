package hr.foi.rampu.snackalchemist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.snackalchemist.R
import hr.foi.rampu.snackalchemist.adapters.ArticlesAdapter
import hr.foi.rampu.snackalchemist.dc.Article

class Articles : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var articlesAdapter: ArticlesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = view.findViewById(R.id.recyclerViewArticles)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val articlesList = listOf(
            Article("Suho voće", "Izbor suhog voća doista je šarolik, no znate li koliko je ono doista zdravo?.Ono je itekako bogato hranjivim sastojcima i vlaknima, ali i bogat izvor antioksidansa, folne kiseline i B-vitamina. Jedno je istraživanje provedeno na 13 tisuća ljudi pokazalo kako ljudi koji konzumiraju sušeno voće imaju bolji unos hranjivih sastojaka od onih koji ga ne jedu.Međutim, ne smijemo zaboraviti kako je ovo voće bogato i prirodnim šećerima što je za sportaše koji trebaju brzi unos energije zapravo i vrlo korisno, no na oprezu bi trebali biti svi koji paze na unos šećera, ugljikohidrata i općenito kalorija.", "11.10.2023", R.drawable.suhovoce),
            Article("Dr.sc. Eva Pavić, dipl.ing. univ. spec.", "Svakodnevni izbori o tome što i koliko ćete jesti te općenito kako ćete živjeti mogu dugoročno utjecati na zdravlje. Pravilna prehrana, redovna tjelesna aktivnost (više kretanja, manje sjedenja; minimalno 150 minuta tjelesne aktivnosti tjedno), duljina trajanja sna, prestanak pušenja i ograničena konzumacija alkohola glavne su odrednice za dugovječnost i zdravlje", "11.10.2023", R.drawable.evapapic),
            Article("Mag.clin.nutr. Kristina Babić", "Mnogi sežu za strogim režimom prehrane i raznovrsnim dijetama koje imaju efekt brzog povratka izgubljenih kilograma, sve nam zanima kako i na koji način zdravo mršavjeti i koju vrstu prehrane preporučujete?", "12.03.2023", R.drawable.kristinababic)
        )

        articlesAdapter = ArticlesAdapter(articlesList)
        recyclerView.adapter = articlesAdapter
    }
}

