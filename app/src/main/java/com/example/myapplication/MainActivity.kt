package com.example.myapplication


import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myapplication.R.layout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var data: ArrayList<String> = ArrayList()
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        addData()
        setAdapter("Vertical List")
        initListener()


    }

    private fun initListener() {
        radio_grp.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                radio1.id -> setAdapter("Vertical List")
                radio2.id -> setAdapter("Horizontal List")
                radio3.id -> setAdapter("Grid View with 2 colomns")
                radio4.id -> setAdapter("Staggered View")


            }

        })
        zoomedImage.setOnClickListener {
            zoomedImage.visibility = View.GONE
            view_back.visibility = View.GONE
        }
    }

    private fun setAdapter(s: String) {

        if (s.equals("Vertical List")) {
            label2.text = s + "- Swipe Up/Down"
            mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recycler_view_image.layoutManager = mLayoutManager
        } else if (s.equals("Horizontal List")) {
            label2.text = s + "- Swipe Left/Right"
            mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycler_view_image.layoutManager = mLayoutManager
        } else if (s.equals("Grid View with 2 colomns")) {
            label2.text = s + "- Swipe Up/Down"
            gridLayoutManager = GridLayoutManager(applicationContext, 2)
            recycler_view_image.layoutManager = gridLayoutManager
        } else if (s.equals("Staggered View")) {
            label2.text = s + "- Swipe Up/Down"
            staggeredGridLayoutManager =
                StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL)
            recycler_view_image.layoutManager = staggeredGridLayoutManager
        }
        var adapter = Adapter(this, data, zoomedImage, view_back)
        recycler_view_image.adapter = adapter
    }

    private fun addData() {
        data.add("https://lh3.googleusercontent.com/P6T8MCT6e_g9sO9CaV66epctUnDq0rGoBRNbExx4V6fN38W7rD1U1HPSYjpG1CM_knnf2AhCCn6um2PLwpkdN6IGHAf8-6cCR158BAyjEwu3ngk25yqHTmhjqvwuP29qaSmwYpPXuf_9NTL0kFFwSuUAX5DfAi_AayNuSNr2BdyXE-fxqF5GPlEajGIqGOO6sgoKMCWIQkiZXbrPTWGDOhftIM_tKExYZ-uWIVIXMCogbTdRyK__FFvp8DCItcLRFwNTohdsax0y04tzOWoTqYh7ApsHlQsXcjHWPlt_04pYS257hyPtnNHcdRQ1Sdv20kVsYHp3KTCgcNcs831STJEUVo2kmJ6KNsyLgAzyxMKRbGTVT9h_Ea5UHD6zpVTTs1A1BfpuokzqMJkEqaeCbxodXmoJWhggFq1n9zmIezFKtVzdfIh7Vxbx802_JksZU3z9I7l1_Rbku29tMPmffutFVHon6kATGQwY8qWKdFc1DcvzOIurJFKenL-2LnhCvajSZ9hZz3FSmHod43SY5hSegkrH0Hcw7zk5XBYkrzDqd1JvcIHmoIcsntMCkOrXdb7decDFOuUcJzEwdZ7Zi0zmmNHFPCKqj0EwNanESItbZdZ_qs60O1kBDLQ7AalgMdQyy1xRCVS2wK7khNl_DHPXam4G_3f0WTMqQfvlltBYjZdXb7W93-qXAa3pvg=w165-h220-no")
        data.add("https://lh3.googleusercontent.com/E7zVv-h7LnQCnXAuvYwLsGVp1WPFkU7lDOdJXOsOrWH-bbOElI1r3P7yPTIHGuwaPYFVn4_awPJFsb07-hiOmp8t_hayBJ5cfwxPLwIhbt6lz-NwSVEpf-B7DNiWEdeMBB0ER9JesFNlkdj8qxmgn2aSLKDwqvB5iGg3DcbrPVtH5Sz4PcWQHWvcxPebZq05Yr5Bk2UDpqiWx5KITS8kknRRl-Cb3FVPgwthZcKkaotv_xqTcEYpx-za1S4SoDzi1qbzc9BNE0fINe2YBz1fdRrmE80Kw8QNoQ2hD0Kdulh_bLKh3viPVx2NFz8XtxxZv8THmcipByZHy9CpAhJ0QDVuZYIEG2NmyeLA0ViPGIvx75pz1V7Pb6HftrVhiBYHSUN3TsukB1azm7YMd4F0mguAXDGNSS7Yx8B_ZyXoXA3AxQG6j6LMdoj0WXewsj1mh2kGkKAPpcByktA9obOEsP2b_agVMuH6JH2PAMNNwF9csjjqB_iKieeTUOHjy9s0A3hbtUPTUYVwTTx9rZkulQ5bzTedZcfRkVWvrB1AhawNJv0ZiyhGh5a9v0WMMbZY-I26L7CWvaRuVeXuaKIoUJvpsoyqUzuM8ydSdlh8H7nohAPU5GJtnfU2CUEhKWfBDs5ZRyN2ObjpslEFd1Q61BP4GqzaHiZK-QvM8ujVllXtGfMMxGr2Li24QzolQw=w727-h969-no")
        data.add("https://lh3.googleusercontent.com/Mx9PEZg892JDnBHl_enSAoTSgonOZjwr5nxS7Y4L9YGb64RMlF_IyWe_3HkczOxtzk7ncfMeGcIU0n2SlXli8aHqNiz3Yuutq0tVsWIRvECcSY0KRCA50ws5OCwbzoVUgIZ0pdrDYYtvxt96rgPY57w_sE_m547jEtU3Pkai0c8f_Gbj5TWxQtv6Tusxm_AzUGeRo75op02o7PiLIzEHkCrtfrCQovy9jwjVYUOrql5OZPaiXO1V5K1KhkTxFShFmLCHNkgKkadBkAA_CXrITNFHmE-rTF2oFxTkO5LL5ZL1V4nuHZyhg-8BED_1ktOVpFlpDYt6Ol_51D-MqO0WuqXJ3xcdeFBeV-lM-5qNmQcYgNw9VU4qPDxYSmWUMf1ZO6Em2QQK0M7WJVCIet040zfzjMCKR-5NL-YIbSOo5_sh4wWLStjiXvHcHd3-Fa9Ed2MkzinCKvnngBmF_MgUBQn8oEdaKKK0sp8MLauwB-Jx7GnufVpoMHQF46uXsBQ61A2pNqGhIMJbgb10cuse8YMjXJrdrGsLK6ONoz9ALCOVJAC_aXvb44pl9_MPMH_-5_pq_e0MfbHpzhqTks-YtDuTNgCJewZ2TlVcmfF3C4a_9w4Uu6VysoP_Wowawe1seQXK65ui2sCkBq1RZTP9ckzq9HRf4WJvkdeKHjNH2yyNKGoM5nmHWGjvb-S7cg=w401-h227-no")
        data.add("https://lh3.googleusercontent.com/4Tyfvfs1Cg5RGcTQJ85OzDDu6M4LwSC2xILh_WQP-P8jzuHgSbFRgctR4HAMlb-lG8EzO_lWy-UVNLbjcYn46vfsN1NawmjIQXNE3W_Uf3aJ3StVCG53FNxoemX5Xh-OJ7ffSzapC7DkmxVeHWCmcSl1csWaiBCvr_OfGFUtvwUrl7YlYuMM1lpAWiyoQbNglFrXzmnZ9ztPVqEuI4_jiY06pF2hzM7isKs-GdJatGdIwa2RiG28DXuRhS-ydogkxyo1_htdIem2R6xX3AOIJtIMtJV8p6wsAGY64fVcgpe5Q3zytStv0qKxzR55qvaOu04Yutvsm-dp5XIUqq2ONZqMqUsPFc_JdpH03d2KJ-W42wEwqLA8eMbyXFaqaRjE-8tX0W0E9SMypNik9B5922YC219ntKQVP--Ec1J8kaLMjCFpYELgFVFZ3PQZG1MVErS6QdZvZ5EUJ5ly2dLucI0gXaz2mV4t-GCC1ljROJQpHFJtAvf5rfJX5R85RN86Hpz4_1QlWygQdYzNFyZ207LK9UD7LRpyVHdZKA7K5PRa_OBeMgJEgZbqLjW5TG6keIaKNL2urFfjQ85fdb_yW3zXk_hCyLB-eWqyXqXDXGy8Rzt-lpqLuRbsKPY0QB5TkU4fjgKlS7nG06Ewf2UqpKosk9DidWJpg2uTo4uGNGka4wXp4GxZ3Eem9Cen-Q=w1920-h887-no")
        data.add("https://lh3.googleusercontent.com/IGXdcoJFFWLJoonKkyOS1r4dTKPZqDNOL2Ggx3Zq8TqFUrM6RDIKMV3NL9xn-AJKXeoEO_NR4HrehlSJQc85JGLqLhe8RlqjTtbDh5k7X1SJfWTT5D-UsMYq4FfeWs01tOxr3Di43ZML4N3CqXUGGCTiJ7dHTAfG7fP1wdBMgZgxf7lxvkpyBg3HSuvsEQ6KYzYegs8GSDRn1zYBUaR_HobmTgq4Fw73Ta5eZrHAgq9gWH1c6XOy9IgohXnJqy1mnf9jz1uQT07w2lYRaBiWRcWYvToQxcqo8AzwP8aA6NIM92QTBwLh3lxVyt_RQ-XGaONfhx5nNzqc-PltfP0YUhF0yAGlzXll9YFs5Z_TaTPhUBDjIUlQ1rciKdmEL2CRkorpsvuOwixl_6vC2mMaPO7Qo2MDjR4-e3LAB2i1hn-E5le-tTJt-F-_5HNoXW-lT833zBbm_3B567a-9MPwu6VT9B-wGKtqZtHs6nyGvrbaN9I1rrUylWukWXSxYOGOZYfMA7sa63n_p75yoOq3m9JM41Z919SxaTat7PH0JTqPJB_BF-VFHMkVCbdhijSmRRVfDSd4BKNmLy5PTzbiy9vVp3ki5XeOajCjpoLOyTjtCasheI1j_IF0AAgiqI_YNFGefCT6AArh96D5B__IwvauTw-NI-Z99BfDGPA8YF0lnmeH1-xASdKwwjGoiQ=w727-h969-no")
        data.add("https://lh3.googleusercontent.com/KOcw--WiEQuSNJuPGlNLueocwPelSmXGG4tmlvGtBbw3TbJoxRdZfjFrDLhmTjYcP2yk_Hbd_nwjk54ntDR7d0n_PXAW810PEKpZDYB7P4fjc-GRvy-9eQHa9qf2kqrANk6fMXFxrhbwvfg3k-lxoneOwv1JF4xTfkoyOnREaHjPj9uohQXnr-FakH2FtgIY_rSUsGVdmk0Qiifm4ZJm1g04UgfT8T7PGRgU6jL3EtZosjPnYxcI0WEwPN43IitHmEjqBco5PEv2GVM1yVkGyGvnMUy3gbgrVfS8ybBDl4qjrAc3eJIdxKFsPEOWLwGxJVyWIBtNn_Bgn-eEndrThFQiKusrizAF6DdW_yKgJPPBtIVU6zXCbW7ip9ZI1uOnLGwgsE3U67y97kLDXvG4Z1GAL6lpq-AJi6b2H00TiidOkSq24UKPrnsn48tj6KCAHsjvtsJnFxCqE6Do5pJtaT0nlgSF5mUn0rAwe2F6QBTScWMo0GZPKRsrgyTIFd02I5UpfHA0HNMp82XQZegOXU6JnESd1MlSXk0t5rEYKsg06oSeI0KOwu6hUpfaAEzZWlovOAVqDmdmEA_laTNwaGuph3nj8uZdfEKg6BzIpdhKwj_UwVtO3kr1ptno64Eh7_2LfgT-zSe4BK4c3tLeE1ygeu0xbCHuO_Z6IA-rhH1G_ch9gBFhZfPj1-IfQQ=w727-h969-no")
        data.add("https://lh3.googleusercontent.com/jMvgA9ODjofg9HnrIVzdCVbW5pAYY9mcFtChibtQaFz1alq_cviLH_3U8qBANE0Co9Tlv0GoxwIUUQCBBmOzvPciMdGfs8PyLFc0-KtuJR6jlbTHMdqmP_gq3XX0xPWkYz0D79G6yCVFqYFVyDUeCwjksQ7pImDdw6QN84LJT5OBu0sJLgz079bHI85JscAM53ikPzfZ8q8ykOnrc8Hie-50CBVuAzzJJugpdYKJejARjgy2xhkX9H3y2de4W9T3iLenT73M7h9tnI7T8L2rkF4lxxModOOvPiWIalPaV9pShMAgorfJoYOetH1LJZh21mc_ZIcuWLgGOUifCQzhQhK1Knnrn54xrmhyj-DAJdeTauzFApawMjGZi0Yga4HWgstY48oZJJC0ChEshTq1ileWOHOblvrFC1raUYk1GIPseUbvrYaisM-HjXONE2tvL_7sCvQUB9h9UfK_5GCjvHImwBxmmcPgoKxOXqaLhANzdNm3TrWOwpnxpqvDIjMEsi00D7SMlChTXNuLYatZYTQMfgc5V9by6fM_9SYdEllqZvH8ADyLYq-bptPSggBYRcKGC7ReczIPLmHfGl5NhHl9ZWPWr5CZyvUn2dI1CrCVGC3N0lG1KWfYiU6tLcpMbPT2ucdFiwhLR3-vrOmjBaKzWv2O5Xwp7eIWdGqg2iwXVx9y5_roW_CCHqRm-w=w1024-h768-no")
        data.add("https://lh3.googleusercontent.com/C-VcndEdYQbcTE2Z1F25NkxkGbi6KUe60fvOhvMr68lCMMPZZRxk9S92f35i2N-IG2u-ZXI0zY4mbqeaDvApnm0O31Jvm_uxXpZuYQg2X_IbpyssQORcupZG7EoMDbNOYMjUTbRzs2j-PtTqy_wxJWrLBlr5MAFXt1ZQTgKEvAvFiRs8AkSIAr8Q7qJxur-V2eB98M0XplRcW4u5DiHU2OIEHKiY6SBwRmuYM-yefmWwz5BwwT9u9m61kFu7cJ7YtupW-LZ9X4mov1SfEYeRjBD0ak0WrpGRbHcY8JPJ6dRB-V-t-iJxLCjTWr4YOVNGwZfHXRC302XcGoUExrHt_iBGj-bc074aagy1MUHwczLeTbm2t7-tEaH2cmxbACPhW6TJ1n-EOTM6N2WD4ZoLfUWIVXfyqLnpXVWWWRLloEKPYCDVk7mUkJ_PtZMAARirYrqtRr1c_LuliAe3LswurCC_wtuixeIR5xRKJf4K0GTVr6Bc8IdERt4bl2c1EO2L4KrPlA038d09pVbWWJhM_hOAnBdWL2YVNJxORLaMO3qJHoHjIic5kdzwtLoU3t3U-m-b5Gpk9FdQtTWGNWtcQK3VcjC5TdmgavBWoA7bPBsvpld3_PiCojVUAdKc-m_10DM7KvljOw0cQxBe1KtBvXEWFuyYyGwjfrwbkCkNA1-4b3KfIItreI5WSmj5-Q=w1024-h768-no")
        data.add("https://lh3.googleusercontent.com/RbZxdxrwGpeswkOV7Rua-sBHZuUi1Zd-H56TSF5Z8OJXhu0i1KWPpaa9CgN4i-_qbecj14pLr-3ttZyHkZnPqAnCX_L-CTDo9NLI3MoDFFhvllfxgxDOLgc6X2s0at_8hAMcFodDXT_2aGlTCJT7Uqi5uB3vAyV2V_lYjsF6-x0PXoGkwtAwxJaKX6gp4JpMaL7bOTb3I53irOjt4VS7j5KShFrOemrQKDASp5w2njugjDrON6ipMj1E1lGChj_F3VTSEQ8jXNLgA8HBykt6cDVC5quKAytITBR2-SymoQeqCb79ApU9B2Nppe-kH0GT40gfw1c7jPAdWR8YpEEVbXoQWL3CMF4cqoIyBsdSbQR9BYHNF9lhZ1YIJXHZPqtGw7XfRfHiMXUlAU8Gytfpn8vQC46jScc9aJjH0lwxe6TEzm76vNN0t-69rpaXd8VARzOf3YYz5NFSQF_LrOOCjvK42lH0B7jjBg_OkRT4LwWN__YkaBnsz4GtL5XUNLYBYR-08n3XamXe3-ms6BLHbH31dBIVBpejQPqkhOyy9AuGe6gec75wCHzGsjjgUmd9SaTE_GxVm8w5llaZrbDHVQjgNfRwSWeqO32quxcaWqhCxhPqn9bbzAhEKVQQVaLafhFy5ngEx7rEqWC0SVGxxyHGcFxyvYCowV8g8rFVzd_rJLJ30SG7a5ZpsZgKsw=w1024-h768-no")
        data.add("https://lh3.googleusercontent.com/ad9L56ULGkKGtMs_RMVuPvPfGxt1onW1kOkrtju1SMd95uXKWWgxMrGDkMbQEWL-ULYsMN1HT06NoFnFbxO9vUg2hApfhvFCHp1JuTfHqphBZ3YpA_Zp9RJUtOoKyn0flMv8hVDlr5sDgUzrsB5cM5RC68WzmHbGIEXFQXjb9lT_TwFLxQSJZ5uerYsCKb4GsPlehl1hVJb6Kn8HTp1G45C7uiNZJ7iaOTtD06Xo1q2-mP4AtpWLoDjIsZlaufhJHOGNcb4k-DrEFPyXizAoL76BVk1s1Z_pjPrezTXNkm25iRHo7zr6--lH1CBjsnCicPbD5gN_U5ceJcFPlyWbL0adJD2VxY2gqk5970PnZkZPg9onqw36OOSlxiyWg4BjSNp6vZPL2anaQE_ZwZ_PRE27WjZZK_WuQfITEvpyKXqwBRqb5uCyvX0qSg8xjN-F7C4owKXFIlDFD8ncET5E2fbCVSNl7-az6k0FOMjdQEQakNZjfOujhF87pXSSR4wNecCHyYPyFM6yxVtW1E4ExKD32wDxusTQYzsEE07DL9esdfFSko2px8oXIr22NAYD0ouBQw1H0wQlNnAndExopcqpX4ebix5RA_mHZA0C3kKrxyw0y_9vGgXhiAU7lFZOqau831iuS_qMoT-eBen0jjuwZWl8ZwQo9icx5IjW-OlR7IDYM5LXX9xOiRKWcA=w727-h969-no")

    }

}


