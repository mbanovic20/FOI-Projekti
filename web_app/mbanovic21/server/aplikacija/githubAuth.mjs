import passport from "passport";
import { Strategy as GitHubStrategy } from "passport-github";
const port = 12309;
const url = "http://localhost:" + port;
//const url = "http://spider.foi.hr:" + port;

passport.serializeUser(function (user, cb) {
	cb(null, user.username);
});

passport.deserializeUser(function (username, cb) {
	cb(null, username);
});

function konfigurirajGithubAuth() {
	passport.use(
		new GitHubStrategy(
			{
				clientID: "03c35d098757a5c3e247",
				clientSecret: "ddf3865dab96a9bc6824873310e756c170486909",
				callbackURL: url + "/auth/github/callback",
			},
			(accessToken, refreshToken, profile, cb) => {
				console.log(profile);
				cb(null, profile);
			}
		)
	);
}

const jeAutoriziranPrekoGitHuba = (zahtjev, odgovor, next) => {
	if (zahtjev.user && zahtjev.session.nacinPrijave === "github") {
		console.log("Pristup odbijen korisniku autoriziranom preko GitHub-a.");
		return odgovor.status(403).send("Pristup odbijen.");
	} else {
		console.log(
			"Pristup dopušten korisniku autoriziranom preko baze ili neautoriziranom korisniku."
		);
		next();
	}
};

export { passport, konfigurirajGithubAuth, jeAutoriziranPrekoGitHuba };
