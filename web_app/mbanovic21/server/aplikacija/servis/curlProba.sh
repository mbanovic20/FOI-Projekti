port=12000
server="localhost"
echo "GET"
curl -X GET "http://$server:$port/baza/korisnici/"
echo ""
echo "GET"
curl -X GET "http://$server:$port/baza/korisnici/admin"
echo ""
echo "POST"
curl -X POST "http://$server:$port/baza/korisnici/" -H 'Content-Type: application/json' -d '{"korime":"testniKorisnik", "lozinka":"123", "email":"test3@foi.unizg.hr", "ime":"Test", "prezime":"test". "Datum_rodjenja":"2023-11-10", "tipKorisnika_id":"2"}'
echo ""
echo "DELETE"
curl -X DELETE "http://$server:$port/baza/korisnici/testniKorisnik"
echo ""