package pl.dawidpalka;

public class Sea {

    private Ship[][] sea = new Ship[10][10];

    public boolean addShip(Ship ship, int longitude, int latitude) {

        if(Ship.Position.horizontal.equals(ship.getPosition())){

            if(ship.getLength()+latitude > sea[0].length || latitude < 0){
                return false;
            }

            if(longitude > sea.length-1 || longitude < 0){
                return false;
            }


            for (int i = 0; i < ship.getLength(); i++) {

                //Check field on left
                if(latitude+i != 0 && sea[longitude][latitude+i - 1] != null){
                    return false;
                }
                //Check field on top
                if(longitude != 0 && sea[longitude-1][latitude+i] != null){
                    return false;
                }
                //Check field on bottom
                if(longitude != sea.length-1 && sea[longitude+1][latitude+i] != null){
                    return false;
                }
                //Check field on right
                if(latitude+i != sea[longitude].length-1 && sea[longitude][latitude+i+1] != null){
                    return false;
                }
            }

            for (int i = 0; i < ship.getLength(); i++) {
                this.sea[longitude][latitude+i] = ship;
            }
            ship.setLongitude(longitude);
            ship.setLatitude(latitude);
            return true;

        }else{
            if(ship.getLength()+longitude > sea.length || longitude < 0){
                return false;
            }
            if(latitude > sea[longitude].length-1 || latitude < 0){
                return false;
            }


            for (int i = 0; i < ship.getLength(); i++) {

                //Check 1 field on left
                if(latitude != 0 && sea[longitude+i][latitude - 1] != null){
                    return false;
                }
                //Check 1 field on top
                if(longitude != 0 && sea[longitude+i -1][latitude] != null){
                    return false;
                }
                //Check 1 field on right
                if(latitude != sea[longitude].length-1 && sea[longitude+i][latitude+1] != null){
                    return false;
                }
                //Check length+1 field on bottom
                if(longitude+i != sea.length-1 && sea[longitude+i+1][latitude] != null){
                    return false;
                }
            }

            for (int i = 0; i < ship.getLength(); i++) {
                this.sea[longitude+i][latitude] = ship;
            }
            ship.setLongitude(longitude);
            ship.setLatitude(latitude);
            return true;
        }
    }

    public Ship shot(int longitude, int latitude, Player player) {

        if(this.sea[longitude][latitude] != null){
            Ship ship = this.sea[longitude][latitude];
            ship.hit();
            player.setPoints(player.getPoints()+1);
            return ship;
        }
        return null;
    }

    public Ship[][] getSea() {
        return sea;
    }

    public void setSea(Ship[][] sea) {
        this.sea = sea;
    }
}
