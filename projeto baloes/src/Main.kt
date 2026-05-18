fun ganhouJogo(tabuleiroVazio: Array<Array<String?>>): Boolean {
    if (eVitoriaHorizontal(tabuleiroVazio) ||
        eVitoriaVertical(tabuleiroVazio) ||
        eVitoriaDiagonal(tabuleiroVazio)
    ) {
        return true
    }
    return false
}
fun eVitoriaHorizontal(tabuleiro: Array<Array<String?>>): Boolean {
    for (linha in tabuleiro) {
        var contador = 0
        var ultimoSimbolo: String? = null
        for (celula in linha) {
            if (celula != null && celula == ultimoSimbolo) {
                contador++
                if (contador == 4) return true
            } else {
                contador = 1
                ultimoSimbolo = celula
            }
        }
    }
    return false
}

fun eVitoriaVertical(tabuleiro: Array<Array<String?>>): Boolean {
    val numColunas = tabuleiro[0].size
    for (coluna in 0 until numColunas) {
        var contador = 0
        var ultimoSimbolo: String? = null
        for (linha in tabuleiro) {
            if (linha[coluna] != null && linha[coluna] == ultimoSimbolo) {
                contador++
                if (contador == 4) return true
            } else {
                contador = 1
                ultimoSimbolo = linha[coluna]
            }
        }
    }
    return false
}
fun eVitoriaDiagonal(tabuleiro: Array<Array<String?>>): Boolean {
    for (linha in 0 ..< (tabuleiro!!.size - 3)) {
        for (coluna in 0 ..< (tabuleiro[0].size - 3)) {
            if (tabuleiro[linha][coluna] != null  &&
                tabuleiro[linha][coluna] == tabuleiro[linha + 1][coluna + 1] &&
                tabuleiro[linha][coluna]  ==  tabuleiro[linha + 2][coluna + 2] &&
                tabuleiro[linha][coluna]  == tabuleiro[linha + 3][coluna + 3]
            ) return true
        }
    }

    for (linha in 0 ..< (tabuleiro.size - 3)) {
        for (coluna in 3 ..< tabuleiro[0].size) {
            if (tabuleiro[linha][coluna] != null &&
                tabuleiro[linha][coluna]  == tabuleiro[linha + 1][coluna - 1] &&
                tabuleiro[linha][coluna]  == tabuleiro[linha + 2][coluna - 2] &&
                tabuleiro[linha][coluna]  == tabuleiro[linha + 3][coluna - 3]
            ) return true
        }
    }
    return false
}
fun contaBaloesLinha(tabuleiroVazio: Array<Array<String?>>, numLinhas: Int): Int {
    var contador = 0
    if (numLinhas in 0 until tabuleiroVazio.size) {
        for (coluna in 0 until  tabuleiroVazio[numLinhas].size) {
            if (tabuleiroVazio[numLinhas][coluna] != null) {
                contador++
            }
        }
    }
    return contador
}


fun contaBaloesColuna(tabuleiroVazio: Array<Array<String?>>, coluna: Int): Int {
    var contador = 0
    for (linha in tabuleiroVazio) {
        if (linha[coluna] != null) {
            contador++
        }
    }
    return contador
}
fun eEmpate(tabuleiroVazio: Array<Array<String?>>): Boolean {
    val estaCheio = tabuleiroVazio.all { linha -> linha.all { it != null } }
    if (estaCheio) {
        return true
    } else
        return false
}
fun main() {
    menu()
}
fun menu() {
    println("\n" + "Bem-vindo ao jogo \"4 Baloes em Linha\"!" + "\n")
    var tabuleiroVazio: Array<Array<String?>>? = null
    var nomeJogador1 = ""
    var numLinhas = 0
    var numColunas = 0
    var valido = true
    while (true) {
        if (valido) {
            println("1. Novo Jogo")
            println("2. Gravar Jogo")
            println("3. Ler Jogo")
            println("0. Sair\n")
        }
        when (readln().toIntOrNull()) {
            0 -> {
                println("A sair...")
                return
            }

            1 -> {
                val (linhas, colunas) = validacaoTabuleiro()
                numLinhas = linhas
                numColunas = colunas
                nomeJogador1 = registrarJogador(1)
                tabuleiroVazio = criaTabuleiroVazio(numLinhas, numColunas)

                jogoAndamento(tabuleiroVazio, numLinhas, numColunas, nomeJogador1)
                valido = true
            }
            2 -> {
                if (tabuleiroVazio == null) {
                    println("Funcionalidade Gravar nao esta disponivel")
                    valido = false
                } else {
                    val nomeFicheiro = introduzirFicheiro()
                    gravaJogo(nomeFicheiro,tabuleiroVazio, nomeJogador1)
                    println("Tabuleiro $numLinhas"+ "x$numColunas gravado com sucesso")
                    valido = false
                }

            }
            3 -> {
                val nomeFicheiro = introduzirFicheiro()
                val (nomeJogador, tabuleiroLido) = leJogo(nomeFicheiro)
                if (tabuleiroLido.isNotEmpty()) {
                    val numLinhasLido = tabuleiroLido.size
                    val numColunasLido = tabuleiroLido[0].size
                    println("Tabuleiro $numLinhasLido" + "x$numColunasLido lido com sucesso!")
                    jogoAndamento(tabuleiroLido, numLinhasLido, numColunasLido, nomeJogador)
                    valido = true
                }
            }
            else ->{println("Opcao invalida. Por favor, tente novamente.")
                valido = false
            }
        }
    }
}
fun validaTabuleiro(numLinhas: Int, numColunas: Int): Boolean {
    return (numLinhas == 5 && numColunas == 6) ||
            (numLinhas == 6 && numColunas == 7) ||
            (numLinhas == 7 && numColunas == 8)
}
fun nomeValido(nomeJogador: String): Boolean {
    return nomeJogador.length in 3..12 && ' ' !in nomeJogador
}

fun registrarJogador(numeroJogador: Int): String {
    var nomeJogador: String? = null
    while (nomeJogador == null) {
        println("Nome do jogador $numeroJogador:")
        val nomeInput = readln()
        if (nomeValido(nomeInput)) {
            nomeJogador = nomeInput
        } else {
            println("Nome de jogador invalido")
        }
    }
    return nomeJogador
}
fun criaTopoTabuleiro(numColunas: Int): String {
    val cantoEsquerdo = '\u2554'
    val cantoDireito = '\u2557'
    val esqToDireita = '\u2550'
    var contadorEsqToDirei = 0
    var topoTabela = "$cantoEsquerdo"
    while (contadorEsqToDirei < (numColunas * 4 - 1)) {
        topoTabela += esqToDireita
        contadorEsqToDirei++
    }
    topoTabela += cantoDireito
    return topoTabela
}
fun criaLegendaHorizontal(numColunas: Int): String {
    var legenda = "  "
    for (contadorLegendas in 0 ..< numColunas) {
        if (contadorLegendas > 0) {
            legenda += " | "
        }
        legenda += ('A' + contadorLegendas)
    }
    legenda += "  "
    return legenda
}
fun criaTabuleiro(tabuleiroVazio: Array<Array<String?>>, mostraLegenda: Boolean = true): String {
    val numColunas = tabuleiroVazio[0].size
    val topoTabela = criaTopoTabuleiro(numColunas)
    var contadorLinhas = 0
    var tabuleiroInteiro = "$topoTabela\n"

    for (linha in tabuleiroVazio) {
        var linhaAtual = "\u2551"
        for (i in 0 until numColunas) {
            val celula = linha[i]
            linhaAtual += " " + "${celula ?: " "}" + " "
            if (i < numColunas - 1) linhaAtual += "|"
        }
        linhaAtual += "\u2551"
        contadorLinhas++
        if (contadorLinhas == tabuleiroVazio.size) {
            tabuleiroInteiro += linhaAtual
        } else {
            tabuleiroInteiro += linhaAtual + "\n"
        }
    }
    if (mostraLegenda ) {
        tabuleiroInteiro += "\n" + criaLegendaHorizontal(numColunas)
    }

    return tabuleiroInteiro
}

fun processaColuna(numColunas: Int, coluna: String?): Int? {
    val ultimaLetra = 'A' + numColunas - 1

    if (coluna.isNullOrEmpty() || coluna.length != 1 || coluna[0] !in 'A'..ultimaLetra) {
        return null
    }
    return coluna[0] - 'A'
}

fun legendaJogo(numLinhas: Int, numColunas: Int, nomeJogador: String): String {

    return "\n$nomeJogador: ${balaoVermelho()}\nTabuleiro $numLinhas" + "X$numColunas"
}

fun legendaJogoComputador(numLinhas: Int, numColunas: Int, coluna: Int): String {

    val letraColuna = ('A' + coluna).toString()
    return "\nComputador: ${balaoAzul()}\nTabuleiro $numLinhas" + "X$numColunas\nColuna escolhida: $letraColuna"
}

fun criaTabuleiroVazio(numLinhas: Int, numColunas: Int) : Array<Array<String?>> {
    return Array(numLinhas) { Array<String?>(numColunas){null} }
}
fun colocaBalao(tabuleiro: Array<Array<String?>>, coluna: Int, humano: Boolean): Boolean {
    val balao = if (humano == true) balaoVermelho() else balaoAzul() // Vermelho ou Azul
    for (linha in 0 until  tabuleiro.size) {
        if (tabuleiro[linha][coluna] == null) {
            tabuleiro[linha][coluna] = balao
            return true
        }
    }
    return false
}

fun jogadaNormalComputador(tabuleiroVazio: Array<Array<String?>>): Int {
    var colunaAtual = 0
    for (linha in tabuleiroVazio) {
        for (celula in linha) {
            if (celula == null) {
                colocaBalao(tabuleiroVazio, colunaAtual, humano = false)
                return colunaAtual
            }
            colunaAtual++
        }
        colunaAtual = 0
    }
    return -1
}
fun gravaJogo(nomeDoFicheiro: String,tabuleiro: Array<Array<String?>>, nomeJogador: String,) {
    val file = java.io.File(nomeDoFicheiro)
    val writer = file.printWriter()

    writer.println(nomeJogador)
    var linhaContador = 0
    while (linhaContador < tabuleiro.size) {
        val linha = tabuleiro[linhaContador]
        val linhaStr = StringBuilder()
        var colunaContador = 0
        while (colunaContador < linha.size) {
            val celula = linha[colunaContador]
            linhaStr.append(
                when {
                    celula == null -> ""
                    celula == balaoVermelho() -> balaoHumano() // Para o Humano, usamos "H"
                    celula == balaoAzul() -> balaoComputador() // Para o Computador, usamos "C"
                    else -> celula.trim() // Se não for nenhum dos casos, apenas mantém o valor original
                }
            )
            if (colunaContador < linha.size - 1) {
                linhaStr.append(",")
            }
            colunaContador++
        }
        writer.println(linhaStr.toString())
        linhaContador++
    }
    writer.close()
}
fun leJogo(nomeDoFicheiro: String): Pair<String, Array<Array<String?>>> {
    val ficheiro = java.io.File(nomeDoFicheiro)
    if (!ficheiro.exists()) {
        println("Ficheiro não encontrado.")
        return Pair("", arrayOf()) // Retorna par vazio em caso de erro
    }
    val linhasFicheiro = ficheiro.readLines()
    val nomeJogador = linhasFicheiro.first()

    val numLinhas = linhasFicheiro.size - 1
    val numColunas = linhasFicheiro[1].split(",").size

    val tabuleiro = Array(numLinhas) { arrayOfNulls<String>(numColunas) }

    for (linha in 1 until linhasFicheiro.size) {
        val linhaConteudo = linhasFicheiro[linha].split(",")
        for (coluna in 0 until linhaConteudo.size) {
            val valor = linhaConteudo[coluna].trim()

            when {
                valor.isEmpty() -> tabuleiro[linha - 1][coluna] = null  // Célula vazia
                valor == balaoHumano() -> tabuleiro[linha - 1][coluna] = balaoVermelho()
                valor == balaoComputador() -> tabuleiro[linha - 1][coluna] = balaoAzul()
            }
        }
    }

    return Pair(nomeJogador, tabuleiro)
}


fun explodeBalao(tabuleiroVazio: Array<Array<String?>>, coordenadas:Pair<Int,Int>): Boolean {
    return true
}

fun jogadaExplodirComputador(tabuleiroVazio: Array<Array<String?>>): Pair<Int, Int> {
    return Pair(0, 0)
}

fun jogoAndamento(tabuleiroVazio: Array<Array<String?>>, numLinhas: Int, numColunas: Int, nomeJogador1: String){
    var jogoAcabado = false
    while (!jogoAcabado) {
        val tabuleiro = criaTabuleiro(tabuleiroVazio) + "\n"
        val legenda = legendaJogo(numLinhas, numColunas, nomeJogador1)
        print("$tabuleiro")
        println("$legenda")

        val jogadaHumano = jogadaHumano(tabuleiroVazio, numLinhas, numColunas, nomeJogador1)
        if (jogadaHumano == null) return

        colocaBalao(tabuleiroVazio, jogadaHumano, humano = true)
        println("Coluna escolhida: ${'A' + jogadaHumano}")
        println(criaTabuleiro(tabuleiroVazio))

        if (verificaFimDoJogo(tabuleiroVazio, numLinhas, numColunas, nomeJogador1)) return

        val colunaComputador = jogadaNormalComputador(tabuleiroVazio)
        println(legendaJogoComputador(numLinhas, numColunas, colunaComputador))
        if (ganhouJogo(tabuleiroVazio)) {
            println(criaTabuleiro(tabuleiroVazio))
            println("\n" + "Perdeu! Ganhou o Computador.")
            println()
            return
        }
    }
}
fun eQuaseVitoriaVertical(tabuleiro: Array<Array<String?>>, linhaInicial: Int, coluna: Int): Boolean {

    if (coluna < 0 || coluna >= tabuleiro[0].size) return false
    // Verifica se há espaço suficiente para 3 balões seguidos e uma casa vazia
    if (linhaInicial < 0 || linhaInicial + 3 >= tabuleiro.size) return false
    val balao1 = tabuleiro[linhaInicial][coluna]
    val balao2 = tabuleiro[linhaInicial + 1][coluna]
    val balao3 = tabuleiro[linhaInicial + 2][coluna]
    val balao4 = tabuleiro[linhaInicial + 3][coluna]
    return balao1 != null && balao1 == balao2 && balao1 == balao3 && balao4 == null
}

fun eQuaseVitoriaHorizontal(tabuleiro: Array<Array<String?>>, linha: Int, colunaInicial: Int): Int? {
    if (linha < 0 || linha >= tabuleiro.size) return null
    // Verifica se há espaço suficiente para 3 balões seguidos e uma casa vazia
    if (colunaInicial < 0 || colunaInicial + 3 >= tabuleiro[linha].size) return null

    val cor = tabuleiro[linha][colunaInicial]
    if (cor == null ||
        tabuleiro[linha][colunaInicial + 1] != cor ||
        tabuleiro[linha][colunaInicial + 2] != cor) {
        return null
    }
    return if (tabuleiro[linha][colunaInicial + 3] == null) colunaInicial + 3 else null
}
fun calculaEstatisticas(tabuleiro: Array<Array<String?>>): Array<Int> {
    var totalBaloes = 0
    var totalBaloesAzuis = 0
    var totalBaloesVermelhos = 0
    var totalQuaseVitorias = 0

    val numLinhas = tabuleiro.size
    val numColunas = if (numLinhas > 0) tabuleiro[0].size else 0

    var linhaContador = 0
    while (linhaContador < numLinhas) {
        var colunaContador = 0
        while (colunaContador < numColunas) {
            val celula = tabuleiro[linhaContador][colunaContador]
            if (celula != null) {
                totalBaloes++
                when (celula) {
                    balaoAzul() -> totalBaloesAzuis++
                    balaoVermelho() -> totalBaloesVermelhos++
                }
            }
            // Verifica quase vitórias verticais
            if (eQuaseVitoriaVertical(tabuleiro, linhaContador, colunaContador)) {
                totalQuaseVitorias++
            }
            // Verifica quase vitórias horizontais
            if (eQuaseVitoriaHorizontal(tabuleiro, linhaContador, colunaContador) != null) {
                totalQuaseVitorias++
            }
            colunaContador++
        }
        linhaContador++
    }

    return arrayOf(totalBaloes, totalBaloesAzuis, totalBaloesVermelhos, totalQuaseVitorias)
}
fun sugestaoJogadaNormalHumano(tabuleiro: Array<Array<String?>>): Int? {
    // Verifica a possibilidade de quase vitória vertical
    for (coluna in 0 until tabuleiro[0].size) {
        for (linha in 0 until tabuleiro.size - 3) {
            if (eQuaseVitoriaVertical(tabuleiro, linha, coluna)) {
                return coluna
            }
        }
    }
    for (linha in 0 until tabuleiro.size) {
        for (coluna in 0 until tabuleiro[linha].size - 3) {
            val sugestaoHorizontal = eQuaseVitoriaHorizontal(tabuleiro, linha, coluna)
            if (sugestaoHorizontal != null) {
                return sugestaoHorizontal
            }
        }
    }

    return null
}
fun balaoVermelho(): String = "\u001b[31mϙ\u001B[0m"
fun balaoAzul(): String = "\u001b[34mϙ\u001B[0m"
fun balaoHumano(): String = "H"
fun balaoComputador(): String = "C"
fun introduzirFicheiro(): String {
    println("Introduza o nome do ficheiro (ex: jogo.txt)")
    return readln()
}
fun jogadaHumano(tabuleiroVazio: Array<Array<String?>>, numLinhas: Int, numColunas: Int, nomeJogador1: String): Int? {
    val ultimaLetra = 'A' + numColunas - 1
    var jogadaHumano: Int? = null
    while (jogadaHumano == null) {
        println("Coluna? (A..$ultimaLetra):")
        val colunaInput = readln().uppercase()
        if (colunaInput == "?") {
            val sugestao = sugestaoJogadaNormalHumano(tabuleiroVazio)
            if (sugestao == null) {
                println("Nao existe uma sugestao de jogada")
            } else {
                val letraColuna = 'A' + sugestao
                println("Sugestao de jogada na coluna: $letraColuna")
            }
        }
        if (colunaInput == "SAIR") {
            println()
            return null
        }
        if (colunaInput == "GRAVAR") {
            println("Introduza o nome do ficheiro (ex: jogo.txt)")
            val nomeFicheiro = readln()
            gravaJogo(nomeFicheiro, tabuleiroVazio, nomeJogador1)
            println("Tabuleiro $numLinhas" + "x$numColunas gravado com sucesso")
            println()
            return null
        }
        val colunaIndex = processaColuna(numColunas, colunaInput)
        if (colunaIndex != null && colunaInput != "?") {
            if (contaBaloesColuna(tabuleiroVazio, colunaIndex) < numLinhas) {
                jogadaHumano = colunaIndex
            } else {
                println("Coluna invalida")
            }
        } else if (colunaInput != "?") {
            println("Coluna invalida")
        }
    }
    return jogadaHumano
}
fun verificaFimDoJogo(tabuleiroVazio: Array<Array<String?>>, numLinhas: Int, numColunas: Int, nomeJogador1: String): Boolean {
    if (eEmpate(tabuleiroVazio)) {
        println("\n" + "Empate!")
        println()
        return true
    }
    if (ganhouJogo(tabuleiroVazio)) {
        println("\n" + "Parabens $nomeJogador1! Ganhou!")
        println()
        return true
    }
    return false
}
fun validacaoTabuleiro(): Pair<Int, Int> {
    var tabuleiroValido = false
    var numLinhas = 0
    var numColunas = 0

    while (!tabuleiroValido) {
        var numLinhasInput: Int? = null
        var numColunasInput: Int? = null

        while (numLinhasInput == null) {
            println("Numero de linhas:")
            numLinhasInput = readln().toIntOrNull()
            if (numLinhasInput !in 5..7) {
                println("Numero invalido")
                numLinhasInput = null
            }
        }

        while (numColunasInput == null) {
            println("Numero de colunas:")
            numColunasInput = readln().toIntOrNull()
            if (numColunasInput == null || numColunasInput <= 0) {
                println("Numero invalido")
                numColunasInput = null
            }
        }

        if (!validaTabuleiro(numLinhasInput, numColunasInput)) {
            println("Tamanho do tabuleiro invalido")
        } else {
            tabuleiroValido = true
            numLinhas = numLinhasInput
            numColunas = numColunasInput
        }
    }

    return Pair(numLinhas, numColunas)
}