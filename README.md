# Zabezpieczanie danych w aplikacjach Java

Repozytorium zawiera materiały z warsztatu [Stacja.IT](https://stacja.it/).

**UWAGA** Ze względu na prostotę przykładów zwracane wartości i zgłaszane wyjątki
z metod zwykle nie są sprawdzane. Należy to zmienić w kodzie produkcyjnym 💣!

## Przygotowanie do warsztatu

Środowisko najlepiej przygotować i sprawdzić **przed** warsztatem, nie będzie to
elementem spotkania.

Zalecane jest użycie JDK w wersji 11 lub wyższej.

Projekt można otworzyć w środowisku [IntelliJ IDEA](https://www.jetbrains.com/idea/).

W katalogu `lib` znajduje się dostawca [Bouncy Castle](https://www.bouncycastle.org/).

Podczas warsztatu użyjemy również aplikacji [KeyStore Explorer](https://keystore-explorer.org/),
nie jest ona częścią tego repozytorium i można ją zainstalować niezależnie
(paczki są do pobrania ze strony projektu). Jej instalacja nie jest bezwzględnie wymagana
tj. bez niej można w pełni zrealizować ćwiczenia podczas warsztatu.

### Test

Uruchomienie testowej aplikacji `playground.Test` powinno zwrócić wynik

    Algorithm: AES
    Format: RAW
    Provider: BC - BouncyCastle Security Provider v1.72
    Decryption: SUCCESS

Jeśli aplikacji nie da się skompilować lub wynik jest inny należy sprawdzić czy
biblioteki z katalogu `lib` są uwzględnione w konfiguracji projektu (klawisz `F4`).
