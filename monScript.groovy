imgNumber=2
//*****************
imgNumber=(int)(Math.random()*10)
if (imgNumber>3) imgNumber=3
if (imgNumber<=0) imgNumber=1

//*****************

vitesse=(int)(Math.random()*50)
ballRayon=(int)(Math.random()*30)

println ("Numero d'image : "+imgNumber)
println ("Vitesse : "+vitesse)
println ("Rayon : "+ballRayon)