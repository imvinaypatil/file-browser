import * as React from "react";

import {
    Alignment,
    Button,
    Classes,
    H5,
    Navbar,
    NavbarDivider,
    NavbarGroup,
    NavbarHeading,
    Switch,
} from "@blueprintjs/core";


export default class MyNavbar extends React.PureComponent {
   
     render() {
       
        return (
            
                <Navbar>
                    <NavbarGroup align={Alignment.LEFT}>
                        <NavbarHeading>File Browser</NavbarHeading>
                        <NavbarDivider />
                        <Button className={Classes.MINIMAL} icon="home" text="Home" />
                        <Button className={Classes.MINIMAL} icon="document" text="Files" />
                    </NavbarGroup>
                </Navbar>
            
        );
    }
}