-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 23 Sty 2022, 17:59
-- Wersja serwera: 10.4.21-MariaDB
-- Wersja PHP: 7.4.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `java_12`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `katalogi`
--

CREATE TABLE `katalogi` (
  `idKatalogu` int(11) NOT NULL,
  `katalog` text COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `katalogi`
--

INSERT INTO `katalogi` (`idKatalogu`, `katalog`) VALUES
(1, '/tmp/A1'),
(2, '/tmp/A2');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pliki`
--

CREATE TABLE `pliki` (
  `idPliku` int(11) NOT NULL,
  `idKatalogu` int(11) NOT NULL,
  `plik` text COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `katalogi`
--
ALTER TABLE `katalogi`
  ADD PRIMARY KEY (`idKatalogu`);

--
-- Indeksy dla tabeli `pliki`
--
ALTER TABLE `pliki`
  ADD PRIMARY KEY (`idPliku`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `katalogi`
--
ALTER TABLE `katalogi`
  MODIFY `idKatalogu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT dla tabeli `pliki`
--
ALTER TABLE `pliki`
  MODIFY `idPliku` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
