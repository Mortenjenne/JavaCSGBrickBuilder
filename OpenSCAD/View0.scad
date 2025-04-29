translate([0.0, 48.0, 0.0])
{
    rotate([90.0, 0.0, 0.0])
    {
        linear_extrude(height = 48.0, twist = 0.0, scale = 1.0, slices = 1, center = false)
        {
            M405();
        }
    }
}

module M405()
{
    scale([24.0, 24.0])
    {
        M402();
    }
}

module M402()
{
    polygon
    (
        points =
        [
            [0.0, 0.0], 
            [1.0, 0.0], 
            [0.0, 1.0]
        ],
        paths =
        [
            [0, 1, 2]
        ]
    );
}
